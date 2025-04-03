'use strict';
const usernamePage = document.querySelector("#username-page");
const chatPage = document.querySelector("#chat-page");
const usernameForm = document.querySelector("#usernameForm");
const messageForm = document.querySelector("#messageForm");
const messageInput = document.querySelector("#message");
const chatArea = document.querySelector("#chat-messages");
const connectingElement = document.querySelector(".connecting");
const logoutButton = document.querySelector("#logout");

let stompClient = null;
let nickname = null;
let fullname = null;
let selectedUserId = null;

function connect(event) {
	event.preventDefault();
	console.log("Connecting...");

	nickname = document.querySelector("#nickname").value.trim();
	fullname = document.querySelector("#fullname").value.trim();

	if (nickname && fullname) {
		usernamePage.classList.add("hidden");
		chatPage.classList.remove("hidden");

		const socket = new SockJS("/ws");
		stompClient = Stomp.over(socket);

		stompClient.connect({}, onConnected, onError);
	}
}


const onConnected = () => {
	console.log("WebSocket connected");

	stompClient.subscribe(`/user/${nickname}/queue/messages`, onMessageReceived);
	stompClient.subscribe('/user/public', onMessageReceived);

	// Register user
	stompClient.send('/app/user.addUser', {},
		JSON.stringify({ nickname: nickname, fullname: fullname, status: 'ONLINE' })
	);

	document.querySelector("#connected-user-fullname").textContent = fullname;
	/* findAndDisplayConnectedUsers();*/
	setInterval(findAndDisplayConnectedUsers, 2000);
};

const findAndDisplayConnectedUsers = async () => {
	try {
		const response = await fetch("/users");
		let users = await response.json();

		// Ensure case matches backend fields (nickname, fullname)
		users = users.filter(user => user.nickname !== nickname);

		let userList = document.querySelector("#connectedUsers");
		userList.innerHTML = ""; // Clear previous users

		users.forEach(user => appendUserElement(user, userList));
	} catch (error) {
		console.error("Error fetching users:", error);
	}
};

const appendUserElement = (user, connectUserList) => {
	const listItem = document.createElement('li');
	listItem.classList.add('user-item');
	listItem.id = user.nickname; // Ensure it matches backend data

	const userImage = document.createElement('img');
	userImage.src = '../img/user_icon.png';
	userImage.alt = user.fullname;

	const usernameSpan = document.createElement('span');
	usernameSpan.textContent = user.fullname + "  -";

	const statusSpan = document.createElement('span');
	statusSpan.textContent = user.status; // Show user status
	console.log(user.status);


	listItem.appendChild(userImage);
	listItem.appendChild(usernameSpan);
	listItem.appendChild(statusSpan);

	listItem.addEventListener("click", userItemClicked);

	connectUserList.appendChild(listItem);
};
const userItemClicked = (event) => {
	document.querySelectorAll('.user-item').forEach(item => {
		item.classList.remove('active');
	});
	messageForm.classList.remove('hidden');

	const clickeduser = event.currentTarget;
	clickeduser.classList.add('active');

	selectedUserId = clickeduser.getAttribute('id');

    fetchAndDisplayUserChat();

}

const fetchAndDisplayUserChat = async () => {

	const userChatResponse = await fetch(`/messages/${nickname}/${selectedUserId}`);
	const userChat = await userChatResponse.json();

	chatArea.innerHTML = "";

	userChat.forEach(user => {
		displayMessage(user.senderId, user.content);
	})
	chatArea.scrollTop = chatArea.scrollHeight;

}

const displayMessage = (senderId, content) => {
	const messageContainer = document.createElement('div');
	messageContainer.classList.add('message');
	if (senderId == nickname) {
		messageContainer.classList.add('sender');
	}
	else {
		messageContainer.classList.add('receiver');
	}

	const message = document.createElement('p');
	message.textContent = content;
	messageContainer.appendChild(message);
	chatArea.appendChild(messageContainer);
}

const sendMessage = (event) => {
	event.preventDefault();
	const messageContent = messageInput.value.trim();

	if (messageContent && stompClient) {
		const chatMsg = {
			senderId: nickname,
			recieverId: selectedUserId,
			content: messageContent,
			timestamp: new Date().getTime()
		}

		stompClient.send('/app/chat', {}, JSON.stringify(chatMsg));
		displayMessage(nickname, messageContent);
		chatArea.scrollTop = chatArea.scrollHeight;
		messageInput.textContent = "";
	}
}

const onMessageReceived = async (payload) => {
	await findAndDisplayConnectedUsers();
	const message = JSON.parse(payload.body);
	if (selectedUserId && selectedUserId == message.senderId) {
		displayMessage(message.senderId, message.content);
		chatArea.scrollTop = chatArea.scrollHeight;

	}

	if (selectedUserId) {
		document.querySelector(`#${selectedUserId}`).classList.add('active');
	}
	else {
		messageForm.classList.add('hidden');
	}
};

const onlogout = () => {
	console.log("clicked");

	stompClient.send('/app/user.disconnectUser', {},
		JSON.stringify({ nickname: nickname, fullname: fullname, status: 'OFFLINE' })
	);
	window.location.reload();
}
const onError = (error) => {
	console.error("WebSocket Error:", error);
};

messageForm.addEventListener('submit', sendMessage, true);
usernameForm.addEventListener('submit', connect, true);
logoutButton.addEventListener('click', onlogout, true);
window.onbeforeunload = () => onlogout();
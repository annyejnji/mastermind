// Function to set logged-in state
function setLoggedInState(username) {
    localStorage.setItem('loggedInUser', username);
}

// Function to check if user is logged in
function isLoggedIn() {
    return localStorage.getItem('loggedInUser') !== null;
}

// Function to get logged-in username
function getLoggedInUser() {
    return localStorage.getItem('loggedInUser');
}

// Function to log out
function logout() {
    localStorage.removeItem('loggedInUser');
    window.location.reload();
}
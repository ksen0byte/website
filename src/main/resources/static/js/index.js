let logoutButton = document.getElementById("logout-button");

logoutButton.addEventListener("click", () => {
    let logoutPromise = fetch("/logout", {
        method: "POST",
        mode: "no-cors"
    });
});
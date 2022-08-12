import axios from "axios";
import React from "react";
import { Link, useNavigate } from "react-router-dom";

// The registrration component handles the registration form for new users.
// The info is persisted in the database and locally (partial).
//let info;
function RegisterView() {
	const navigate = useNavigate();
	const handleSubmit = (event) => {
		event.preventDefault();

		// validate password
		const p1 = document.getElementById("password1").value;
		const p2 = document.getElementById("password2").value;

		if (p1 === p2) {
			const info = {
				firstName: document.getElementById("firstname").value,
				lastName: document.getElementById("lastname").value,
				email: document.getElementById("email").value,
				username: document.getElementById("username").value,
				password: document.getElementById("password1").value,
			};
			// submit stuff
			console.log("Information being sent:  ", info);
			doRegistration(info);
			// if successful - a string is returned? (as of 3/2)
		} else {
			alert("Sorry, your passwords do not match.");
		}
	};

	// This compares the two password entry fields.  If they do not match,
	// the boxes are given a red border and the submit button is disabled.
	// TODO: Probably want a clearer (text) indicator stating the problem.
	// optional: Make the password type "password", give option to flip between visibility
	const checkPasswordEntry = () => {
		const passwordNodes = document.getElementsByClassName("password-box");
		const password1 = passwordNodes[0].value;
		const password2 = passwordNodes[1].value;

		if (password1 === password2) {
			for (let i = 0; i < 2; i++) {
				passwordNodes[i].classList.remove("password-error");
				passwordNodes[i].classList.add("password-ok");
				document.getElementById("submit").disabled = false;
			}
		} else {
			for (let i = 0; i < 2; i++) {
				// the passwords do not match
				passwordNodes[i].classList.remove("password-ok");
				passwordNodes[i].classList.add("password-error");
				document.getElementById("submit").disabled = true;
			}
		}
	};

	function doRegistration(info) {
		let responseData;
		const url = "http://localhost:8080/";
    
		axios
			.post(`${url}users/`, info)
			.then((response) => {
				console.log(response.data);
				responseData = response.data;
				/* Commented out b/c registration response may change, but code is valid/from login */
				/*
				if (responseData.user_id === null) {
					console.log("Login failed - bad usernmae/password");
					document.getElementById("login-error-box").textContent =
						"Error: incorrect username or password.";
					//alert("Invalid login attempt.");
				} else {
					doLoginToMain();
				} */ // end working, commented-out code
				doLoginToMain();
			})
			.catch((error) => console.error(`Error: ${error}`));
		//console.log(responseData);

	}

	// TODO: this should route back to index page with note to login
	const doLoginToMain = () => {
		// let history = useHistory ();
		navigate("/main");
	};

	return (
		<div className="container-view login-outer-container">
			<div className="login-inner-container">
				<div className="login-content-box">
					
					<h2 className="logo-smaller" id="register-logo">CacheMoney</h2>
					<div id="register-white-box" className="login-white-box">
						<div className="login-white-box-column">

							<div id="registration-name-boxes">
								<div id="box-L" className="reg-name-box">
								<label htmlFor="firstname" id="label-L">
									First name:
								</label>
								<input type="text" name="firstname" id="firstname" />
								</div>

								<div id="box-R" className="reg-name-box">
								<label htmlFor="lastname" id="label-R">
									Last name:
								</label>
								<input type="text" name="lastname" id="lastname" />
								</div>
							</div>

							<div className="reg-field-box">
							<label htmlFor="email">
								Email:
								<span className="detail-text">
									*must be unregistered valid email
								</span>
							</label>
							<input type="text" name="email" id="email" />
							</div>

							<div className="reg-field-box">
							<label htmlFor="username">
								Username: <span className="detail-text">*must be unique</span>
							</label>
							<input type="text" name="username" id="username" />
							</div>

							<div className="reg-field-box">
							<label htmlFor="password">Password:</label>
							<input
								type="text"
								name="password1"
								id="password1"
								className="password-box"
							/>
							</div>
							
							<div className="reg-field-box">
							<label htmlFor="password2">Confirm password:</label>
							<input
								type="text"
								name="password2"
								id="password2"
								className="password-box"
							/>

							</div>

							<input type="submit" value="Register" onClick={handleSubmit} />

						</div>
					</div>
				</div>
			</div>
		</div>
	);
}

export default RegisterView;

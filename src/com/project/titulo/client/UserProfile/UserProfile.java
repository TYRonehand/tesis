package com.project.titulo.client.UserProfile;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.project.titulo.client.ServerService;
import com.project.titulo.client.ServerServiceAsync;
import com.project.titulo.shared.CookieVerify;
import com.project.titulo.shared.DataOptional;
import com.project.titulo.shared.ErrorVerify;
import com.project.titulo.shared.FieldVerifier;
import com.project.titulo.shared.GoToUrl;
import com.project.titulo.shared.MyStyle;
import com.project.titulo.shared.model.User;

public class UserProfile extends Composite {
	/* style */
	private MyStyle ms = new MyStyle();

	// principal labels
	@UiField
	HTML pNameLabel;
	@UiField
	HTML pMailLabel;
	@UiField
	HTML pUniversityLabel;
	@UiField
	HTML pCountryLabel;
	// secondary Labels
	@UiField
	HTML nameLabel;
	@UiField
	HTML lastnameLabel;
	@UiField
	HTML countryLabel;
	@UiField
	HTML ocupationLabel;
	@UiField
	HTML webLabel;
	// edit user
	@UiField
	TextBox nameInput;
	@UiField
	Label labelError1;
	@UiField
	TextBox lastnameInput;
	@UiField
	Label labelError2;
	@UiField
	ListBox countryBox;
	@UiField
	TextBox ocupationInput;
	@UiField
	TextBox webInput;
	@UiField
	Button submitBTN;
	@UiField
	Button cancelBTN;
	// change email
	@UiField
	PasswordTextBox currentPassword1;
	@UiField
	TextBox newEmail;
	@UiField
	TextBox newEmailRepeat;
	@UiField
	Button changeEmailBTN;
	// change password
	@UiField
	PasswordTextBox currentPassword2;
	@UiField
	PasswordTextBox newPassword;
	@UiField
	PasswordTextBox newPasswordRepeat;
	@UiField
	Button changePasswordBTN;
	// close account
	@UiField
	Button closeAccountBTN;

	// info from user
	private User UserInfo;

	// cookies
	private CookieVerify mycookie = new CookieVerify(false);
	// control url
	public GoToUrl url = new GoToUrl();

	// RPC
	private final ServerServiceAsync serverService = GWT
			.create(ServerService.class);

	private static UserProfileUiBinder uiBinder = GWT
			.create(UserProfileUiBinder.class);

	interface UserProfileUiBinder extends UiBinder<Widget, UserProfile> {
	}

	public UserProfile(String iduser) {
		initWidget(uiBinder.createAndBindUi(this));
		getUser(iduser);
		/* Boostrap style */
		this.submitBTN.setStyleName(ms.getButtonStyle(0));
		this.cancelBTN.setStyleName(ms.getButtonStyle(0));
		this.changeEmailBTN.setStyleName(ms.getButtonStyle(0));
		this.changePasswordBTN.setStyleName(ms.getButtonStyle(0));
		this.closeAccountBTN.setStyleName(ms.getButtonStyle(0));
		/* combobox */
		addCountry();
	}

	// add country to combobox
	private void addCountry() {
		String[] countryList = DataOptional.getCountries();
		for (String country : countryList) {
			countryBox.addItem(country.substring(3));
		}
	}

	// load all data from user
	private void getUser(String iduser) {
		serverService.getUserInfo(iduser, new AsyncCallback<User>() {
			@Override
			public void onFailure(Throwable caught) {
				ErrorVerify.getErrorAlert("offline");
			}

			@Override
			public void onSuccess(User result) {
				LoadDataView(result);
			}
		});
	}

	// edit user
	private void setUserInfo() {
		if (nameInput.getValue().length() >= 3
				&& lastnameInput.getValue().length() >= 3) {
			User aux = new User(UserInfo.getId(), UserInfo.getMail(),
					UserInfo.getName(), UserInfo.getLastname(),
					UserInfo.getCountry(), UserInfo.getOcupation(),
					UserInfo.getWeb(), UserInfo.getInstitution(), "",
					UserInfo.getCreation(), "");

			aux.setName(this.nameInput.getValue());
			aux.setLastname(this.lastnameInput.getValue());
			// combo box
			aux.setCountry(countryBox.getItemText(countryBox.getSelectedIndex()));
			aux.setOcupation(this.ocupationInput.getValue());
			aux.setWeb(this.webInput.getValue());
			// connect to servlet and send user
			updateUserInfo(aux);
		} else {
			Window.alert("Name or Lastname are invalid!");
		}
	}

	// load data into the view
	private void LoadDataView(User myuser) {
		// save user info
		this.UserInfo = myuser;
		// primary info label
		if (myuser.getName() != null && myuser.getLastname() != null)
			this.pNameLabel.setHTML("<p>" + myuser.getName() + " "
					+ myuser.getLastname() + "</p>");
		if (myuser.getMail() != null && !myuser.getMail().equals("null"))
			this.pMailLabel.setHTML("<p>" + myuser.getMail() + "</p>");
		if (myuser.getInstitution() != null
				&& !myuser.getInstitution().equals("null"))
			this.pUniversityLabel.setHTML("<p>" + myuser.getInstitution()
					+ "</p>");
		if (myuser.getCountry() != null && !myuser.getCountry().equals("null"))
			this.pCountryLabel.setHTML("<p>" + myuser.getCountry() + "</p>");

		// secondary info label
		if (myuser.getName() != null)
			this.nameLabel
					.setHTML("<div class='bio-row'><p><span>Name </span>: "
							+ myuser.getName() + "</p></div>");
		else
			this.nameLabel
					.setHTML("<div class='bio-row'><p><span>Name </span>: - </p></div>");
		if (myuser.getLastname() != null)
			this.lastnameLabel
					.setHTML("<div class='bio-row'><p><span>Lastname </span>: "
							+ myuser.getLastname() + "</p></div>");
		else
			this.lastnameLabel
					.setHTML("<div class='bio-row'><p><span>Lastname </span>: - </p></div>");
		if (myuser.getCountry() != null)
			this.countryLabel
					.setHTML("<div class='bio-row'><p><span>Country </span>: "
							+ myuser.getCountry() + "</p></div>");
		else
			this.countryLabel
					.setHTML("<div class='bio-row'><p><span>Country </span>: - </p></div>");
		if (myuser.getOcupation() != null)
			this.ocupationLabel
					.setHTML("<div class='bio-row'><p><span>Ocupation </span>: "
							+ myuser.getOcupation() + "</p></div>");
		else
			this.ocupationLabel
					.setHTML("<div class='bio-row'><p><span>Ocupation </span>: - </p></div>");
		if (myuser.getWeb() != null)
			this.webLabel
					.setHTML("<div class='bio-row'><p><span>Web </span>: <a href='"
							+ myuser.getWeb() + "'>My site</a>");
		else
			this.webLabel
					.setHTML("<div class='bio-row'><p><span>Web </span>: - </p></div>");

		// info inputs
		this.nameInput.setValue(myuser.getName());
		this.lastnameInput.setValue(myuser.getLastname());

		// this.countryInput.setValue(myuser.getCountry());
		int index = 0;
		for (int i = 0; i < this.countryBox.getItemCount(); i++) {
			if (this.countryBox.getItemText(i).equals(myuser.getCountry())) {
				index = i;
				break;
			}
		}
		this.countryBox.setItemSelected(index, true);
		this.ocupationInput.setValue(myuser.getOcupation());
		this.webInput.setValue(myuser.getWeb());
	}

	/* UPDATE */
	// update user
	private void updateUserInfo(User myUser) {

		// connect to servlet and send user
		serverService.setUserInfo(myUser, new AsyncCallback<Boolean>() {
			@Override
			public void onFailure(Throwable caught) {
				ErrorVerify.getErrorAlert("offline");
				LoadDataView(UserInfo);
			}

			@Override
			public void onSuccess(Boolean result) {
				if (result) {
					ErrorVerify.getErrorAlert("successupdate");
					url.GoTo("PROFILE",UserInfo.getId(),null);
				} else {
					ErrorVerify.getErrorAlert("offline");
					LoadDataView(UserInfo);
				}

			}
		});
	}

	/* NEW EMAIL */
	// new email
	private void userNewEmail(String iduser, final String currentPass,
			final String newEmail, final String repeatEmail) {
		// verify user acount
		if (currentPassword1.getText().length() >= 6) {
			// current password is valid?
			serverService.authenticateUser(UserInfo.getMail(), currentPass,
					new AsyncCallback<User>() {
						@Override
						public void onFailure(Throwable caught) {
							ErrorVerify.getErrorAlert("offline");
						}

						@Override
						public void onSuccess(User result) {
							// password verify
							if (result != null) {
								verifyUserEmail(result);
							} else {
								ErrorVerify.getErrorAlert("badpassword");
							}
						}
					});
		} else {
			ErrorVerify.getErrorAlert("invalipass");
		}
	}

	// exist mail¡
	private void verifyUserEmail(final User myuser) {
		// email valid format
		if (newEmail.getText().length() >= 6
				&& FieldVerifier.isValidMail(newEmail.getText())) {
			serverService.userExist(newEmail.getText(),
					new AsyncCallback<Boolean>() {

						@Override
						public void onFailure(Throwable caught) {
							ErrorVerify.getErrorAlert("offline");
						}

						@Override
						public void onSuccess(Boolean result) {
							// not exist
							if (!result) {
								// equals emails
								if (newEmail.getText().equals(
										newEmailRepeat.getText())) {
									User aux = new User(UserInfo.getId(),
														newEmail.getText(), 
														UserInfo.getName(),
														UserInfo.getLastname(), 
														UserInfo.getCountry(), 
														UserInfo.getOcupation(), 
														UserInfo.getWeb(), 
														UserInfo.getInstitution(), 
														"",
														UserInfo.getCreation(), 
														"");
									// connect to servlet and send user
									updateUserInfo(aux);// update
								} else {// not equals emails
									ErrorVerify.getErrorAlert("invalidfield");
								}
							} else
								// exist
								ErrorVerify.getErrorAlert("mailexist");
						}
					});
		} else {
			ErrorVerify.getErrorAlert("invalidmail");
		}
	}

	/* NEW PASSWORD */
	private void userNewPassword(User user, final String currentPass,
			final String newpass, final String repeatPass) {
		// verify user acount
		if (currentPass.length() >= 6) {
			// current password is valid?
			serverService.authenticateUser(user.getMail(), currentPass,
					new AsyncCallback<User>() {
						@Override
						public void onFailure(Throwable caught) {
							ErrorVerify.getErrorAlert("offline");
						}

						@Override
						public void onSuccess(User result) {
							// password verify
							if (result != null) {
								if (newPassword.getText().length() >= 6) {
									// equals emails
									if (newPassword.getText().equals(
											newPasswordRepeat.getText())) {
										User aux = new User(result.getId(),
												result.getMail(), 
												result.getName(), 
												result.getLastname(), 
												result.getCountry(), 
												result.getOcupation(), 
												result.getWeb(), 
												result.getInstitution(),
												newPassword.getText(), 
												result.getCreation(), 
												result.getPin());
										// connect to servlet and send user
										updateUserInfo(aux);// update
									} else {// not equals emails
										ErrorVerify
												.getErrorAlert("diferentpassword");
									}
								} else {
									ErrorVerify.getErrorAlert("invalidfield");
								}
							} else {
								ErrorVerify.getErrorAlert("badpassword");
							}
						}
					});
		} else {
			ErrorVerify.getErrorAlert("invalipass");
		}
	}

	/* DELETE USER INFO */
	private void userDeleteAccount(String iduser) {

		serverService.deleteUserInfo(iduser, new AsyncCallback<Boolean>() {
			@Override
			public void onFailure(Throwable caught) {
				ErrorVerify.getErrorAlert("offline");
			}

			@Override
			public void onSuccess(Boolean result) {
				if (result) {
					// clear all cookies and close active session
					mycookie.delCookiesInfo();
					// load profile
					Window.Location.reload();
				} else {
					ErrorVerify.getErrorAlert("fatal");
				}
			}
		});

	}

	/* INPUT CHANGE-------------------------------- */

	// evento cambio valor input
	@UiHandler("nameInput")
	void handleNameInputChange(ValueChangeEvent<String> event) {
		if (event.getValue().length() < 3) {
			labelError1.setText("minimum lenght 3");
			labelError1.setVisible(true);
		} else {
			labelError1.setVisible(false);
		}
	}

	// evento cambio valor input
	@UiHandler("lastnameInput")
	void handleLastnameInputChange(ValueChangeEvent<String> event) {
		if (event.getValue().length() < 3) {
			labelError2.setText("minimum lenght 3");
			labelError2.setVisible(true);
		} else {
			labelError2.setVisible(false);
		}
	}

	/*
	 * CLICK
	 * EVENTS--------------------------------------------------------------
	 */

	// update data from user
	@UiHandler("submitBTN")
	void onSubmitBTNClick(ClickEvent event) {
		setUserInfo();
	}

	// reset edit user
	@UiHandler("cancelBTN")
	void onCancelBTNClick(ClickEvent event) {
		LoadDataView(this.UserInfo);
	}

	// change user email
	@UiHandler("changeEmailBTN")
	void onChangeEmailBTNClick(ClickEvent event) {
		userNewEmail(this.UserInfo.getId(), currentPassword1.getText(),
				newEmail.getText(), newEmailRepeat.getText());
	}

	// change user pass
	@UiHandler("changePasswordBTN")
	void onChangePasswordBTNClick(ClickEvent event) {
		userNewPassword(this.UserInfo, this.currentPassword2.getText(),
				this.newPassword.getText(), this.newPasswordRepeat.getText());
	}

	// delete user info
	@UiHandler("closeAccountBTN")
	void onCloseAccountBTNClick(ClickEvent event) {
		if (Window.confirm("Delete your account FOREVER?")) {
			userDeleteAccount(this.UserInfo.getId());
		}
	}

}

using iActivity.Model;
using iActivity.Units;
using Newtonsoft.Json.Linq;
using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;

namespace iActivity.ViewModel
{
    public class UserViewModel :BaseViewModel
    {

        Windows.Storage.ApplicationDataContainer _localSettings = Windows.Storage.ApplicationData.Current.LocalSettings;
        
        #region Command Binding
        public DelegateCommand loginCommand { get; set; }
        public DelegateCommand updateCommand { get; set; }
        #endregion

        #region Data Souce Bingding
        private string nameInput;

        public string NameInput
        {
            get { return nameInput; }
            set
            {
                this.SetProperty(ref this.nameInput, value);
            }
        }

        private string nicknameInput;

        public string NickNameInput
        {
            get { return nicknameInput; }
            set
            {
                this.SetProperty(ref this.nicknameInput, value);
            }
        }

        private string usernameInput;

        public string UsernameInput
        {
            get { return usernameInput; }
            set
            {
                this.SetProperty(ref this.usernameInput, value);
            }
        }

        private string oldPasswordInput;

        public string OldPasswordInput
        {
            get { return oldPasswordInput; }
            set
            {
                this.SetProperty(ref this.oldPasswordInput, value);
            }
        }

        private string passwordInput;

        public string PasswordInput
        {
            get { return passwordInput; }
            set
            {
                this.SetProperty(ref this.passwordInput, value);
            }
        }

        private string passwordAgainInput;

        public string PasswordAgainInput
        {
            get { return passwordAgainInput; }
            set
            {
                this.SetProperty(ref this.passwordAgainInput, value);
            }
        }

        public User user;

        public User User
        {
            get { return user; }
            set
            {
                this.SetProperty(ref this.user, value);
            }
        }

        private string loginText;

        public string LoginText
        {
            get { return loginText; }
            set
            {
                this.SetProperty(ref this.loginText, value);
            }
        }

        private string statuText;

        public string StatuText
        {
            get { return statuText; }
            set
            {
                this.SetProperty(ref this.statuText, value);
            }
        }

        private Visibility statuVisibile;

        public Visibility StatuVisibile
        {
            get { return statuVisibile; }
            set
            {
                this.SetProperty(ref this.statuVisibile, value);
            }
        }
        #endregion

        #region Init Method
        public UserViewModel()
        {
            Init();
            user = new Model.User();
            loginCommand = new DelegateCommand(LoginCheck);
            updateCommand = new DelegateCommand(UpdateCheck);
        }
        #endregion

        #region public method
        public async Task<User> AutoLogin()
        {
            StatuVisibile = Visibility.Visible;
            StatuText = "AUTO LOGGING";
            List<KeyValuePair<string, string>> parmList = new List<KeyValuePair<string, string>>();
            parmList.Add(new KeyValuePair<string, string>("username", _localSettings.Values["username"].ToString()));
            parmList.Add(new KeyValuePair<string, string>("password", _localSettings.Values["password"].ToString()));
            string responseText = await App.httpHelper.DoPost("/user/login.do", parmList);
            JObject jo = JObject.Parse(responseText);
            if ("1".Equals(((JObject)jo["msg"])["errcode"].ToString()))
            {
                User.id = Int32.Parse(((JObject)jo["info"])["id"].ToString());
                User.name = ((JObject)jo["info"])["name"].ToString();
                User.nickname = ((JObject)jo["info"])["nickname"].ToString();
                User.password = _localSettings.Values["password"].ToString();
            }
            StatuVisibile = Visibility.Collapsed;
            App.user = user;
            return user;
        }

        public void GetLoginUser()
        {
            NameInput = App.user.name;
            NickNameInput = App.user.nickname;
            UsernameInput = App.user.username;
        }

        public void GetLoginAccount()
        {
            if (_localSettings.Values.ContainsKey("isRemember"))
            {
                string str = _localSettings.Values["isRemember"].ToString();
                if (_localSettings.Values["isRemember"].ToString() == "true")
                {
                    UsernameInput = _localSettings.Values["username"].ToString();
                    PasswordInput = _localSettings.Values["password"].ToString();
                }
            }
        }
        #endregion

        #region private method
        private void Init()
        {
            statuVisibile = Visibility.Collapsed;
            statuText = "正在初始化";
            LoginText = "";
        }


        private async void LoginCheck()
        {
            LoginText = "";
            StatuVisibile = Visibility.Visible;
            StatuText = "Logging......";
            List<KeyValuePair<string, string>> parmList = new List<KeyValuePair<string, string>>();
            parmList.Add(new KeyValuePair<string, string>("username", usernameInput));
            parmList.Add(new KeyValuePair<string, string>("password", passwordInput));
            string responseText = await App.httpHelper.DoPost("/user/login.do", parmList);
            JObject jo = JObject.Parse(responseText);
            string errcode = ((JObject)jo["msg"])["errcode"].ToString();
            string errmsg = ((JObject)jo["msg"])["errmsg"].ToString();
            if (!"1".Equals(errcode))
            {
                if ("-1".Equals(errcode))
                    LoginText = "INCORRENT PASSWORD";
                else
                    LoginText = errmsg;
            }
            else 
            {
                _localSettings.Values["uid"] = ((JObject)jo["info"])["id"].ToString();
                _localSettings.Values["name"] = ((JObject)jo["info"])["name"].ToString();
                _localSettings.Values["nickname"] = ((JObject)jo["info"])["nickname"].ToString();
                _localSettings.Values["username"] = ((JObject)jo["info"])["username"].ToString();
                _localSettings.Values["password"] = passwordInput.ToString();
                user.id = Int32.Parse(((JObject)jo["info"])["id"].ToString());
                user.name = ((JObject)jo["info"])["name"].ToString();
                user.nickname = ((JObject)jo["info"])["nickname"].ToString();
                user.password = passwordInput.ToString();
                App.user = user;
                _localSettings.Values["isRemember"] = "true";
               
                NavigationHelper.Navigation(typeof(iActivity.View.Index), 1);
            }
            StatuVisibile = Visibility.Collapsed;
        }

        private async void UpdateCheck()
        {
            StatuVisibile = Visibility.Visible;
            StatuText = "Updating ......";
            List<KeyValuePair<string, string>> parmList = new List<KeyValuePair<string, string>>();
            parmList.Add(new KeyValuePair<string, string>("name", nameInput));
            parmList.Add(new KeyValuePair<string, string>("nickname", nicknameInput));
            parmList.Add(new KeyValuePair<string, string>("username", usernameInput));
            parmList.Add(new KeyValuePair<string, string>("password", passwordInput));
            parmList.Add(new KeyValuePair<string, string>("oldPassword", oldPasswordInput));
            string responseText = await App.httpHelper.DoPost("/user/update.do", parmList);
            JObject jo = JObject.Parse(responseText);
            string errcode = ((JObject)jo["msg"])["errcode"].ToString();
            string errmsg = ((JObject)jo["msg"])["errmsg"].ToString();
            if(!"1".Equals(errcode)){
                LoginText = errmsg;
            }
            else
            {
                LoginText = "Update success";
            }
            StatuVisibile = Visibility.Collapsed;
        }
        #endregion
    }
}

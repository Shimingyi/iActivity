using Newtonsoft.Json.Linq;
using System;
using System.Collections.Generic;
using System.Text;
using Windows.UI.Xaml;

namespace iActivity.ViewModel
{
    public class ResumeViewModel:BaseViewModel
    {

        private string id;

        #region Command Binding
        public DelegateCommand saveCommand { get; set; }
        #endregion

        #region Data Source Binding
        private DateTime birthInput;

        public DateTime BirthInput
        {
            get { return birthInput; }
            set
            {
                this.SetProperty(ref this.birthInput, value);
            }
        }

        private string collegaInput;

        public string CollegaInput
        {
            get { return collegaInput; }
            set
            {
                this.SetProperty(ref this.collegaInput, value);
            }
        }

        private string idcardInput;

        public string IdcardInput
        {
            get { return idcardInput; }
            set
            {
                this.SetProperty(ref this.idcardInput, value);
            }
        }

        private string mailInput;

        public string MailInput
        {
            get { return mailInput; }
            set
            {
                this.SetProperty(ref this.mailInput, value);
            }
        }

        private string phoneInput;

        public string PhoneInput
        {
            get { return phoneInput; }
            set
            {
                this.SetProperty(ref this.phoneInput, value);
            }
        }

        private int sexInput;

        public int SexInput
        {
            get { return sexInput; }
            set
            {
                this.SetProperty(ref this.sexInput, value);
            }
        }

        private string stunumInput;

        public string StunumInput
        {
            get { return stunumInput; }
            set
            {
                this.SetProperty(ref this.stunumInput, value);
            }
        }

        private string yearInput;

        public string YearInput
        {
            get { return yearInput; }
            set
            {
                this.SetProperty(ref this.yearInput, value);
            }
        }

        private int uidInput;

        public int UidInput
        {
            get { return uidInput; }
            set
            {
                this.SetProperty(ref this.uidInput, value);
            }
        }

        private string loginInput;

        public string LoginInput
        {
            get { return loginInput; }
            set
            {
                this.SetProperty(ref this.loginInput, value);
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
        public ResumeViewModel()
        {
            Init();
            saveCommand = new DelegateCommand(UpdateCheck);
        }
        #endregion

        #region private method
        private async void Init(){
            StatuVisibile = Visibility.Visible;
            StatuText = "DOWNLOADING RESUME ......";
            List<KeyValuePair<string, string>> parmList = new List<KeyValuePair<string, string>>();
            parmList.Add(new KeyValuePair<string, string>("uid", App.user.id.ToString()));
            string responseText = await App.httpHelper.DoPost("/resume/find.do", parmList);
            JObject jo = JObject.Parse(responseText);
            string errcode = ((JObject)jo["msg"])["errcode"].ToString();
            string errmsg = ((JObject)jo["msg"])["errmsg"].ToString();
            if (!"1".Equals(errcode))
            {
                LoginInput = errmsg;
            }
            else
            {
                //BirthInput = DateTime.Parse(((JObject)jo["resume"])["birth"].ToString());
                id = ((JObject)jo["resume"])["id"].ToString();
                CollegaInput = ((JObject)jo["resume"])["college"].ToString();
                MailInput = ((JObject)jo["resume"])["mail"].ToString();
                PhoneInput = ((JObject)jo["resume"])["phone"].ToString();
                SexInput = Int32.Parse(((JObject)jo["resume"])["sex"].ToString());
                StunumInput = ((JObject)jo["resume"])["stunum"].ToString();
                YearInput = ((JObject)jo["resume"])["year"].ToString();
                IdcardInput = ((JObject)jo["resume"])["idcard"].ToString();
                //LoginText = "Saved successed";
            }
            StatuVisibile = Visibility.Collapsed;
        }

        private async void UpdateCheck()
        {
            StatuVisibile = Visibility.Visible;
            StatuText = "SAVING RESUME ......";
            LoginInput = "";
            List<KeyValuePair<string, string>> parmList = new List<KeyValuePair<string, string>>();
            parmList.Add(new KeyValuePair<string, string>("id", id));
            parmList.Add(new KeyValuePair<string, string>("phone",PhoneInput));
            parmList.Add(new KeyValuePair<string, string>("mail",MailInput));
            parmList.Add(new KeyValuePair<string, string>("sex",SexInput.ToString()));
            //parmList.Add(new KeyValuePair<string, string>("birth",BirthInput.ToString()));
            parmList.Add(new KeyValuePair<string, string>("stunum",StunumInput));
            parmList.Add(new KeyValuePair<string, string>("idcard",IdcardInput));
            parmList.Add(new KeyValuePair<string, string>("college",CollegaInput));
            parmList.Add(new KeyValuePair<string, string>("year",YearInput));

            string responseText = await App.httpHelper.DoPost("/resume/update.do", parmList);
            JObject jo = JObject.Parse(responseText);
            string errcode = ((JObject)jo["msg"])["errcode"].ToString();
            string errmsg = ((JObject)jo["msg"])["errmsg"].ToString();
            if(!"1".Equals(errcode)){
                LoginInput = errmsg;
            }
            else
            {
                LoginInput = "Saved successed";
            }
            StatuVisibile = Visibility.Collapsed;
        }
        #endregion

    }
}

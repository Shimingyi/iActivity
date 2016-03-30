using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Text;
using iActivity.Model;
using Windows.UI.Xaml;
using Newtonsoft.Json.Linq;
using System.Threading.Tasks;

namespace iActivity.ViewModel
{
    public class ActivityViewModel : BaseViewModel
    {

        Activity ac;

        #region Command Binding
        public DelegateCommand refreshCommand { get; set; }
        public DelegateCommand joinCommand { get; set; }
        #endregion

        #region Data Source Binding
        private ObservableCollection<iActivity.Model.Activity> activitys;

        public ObservableCollection<iActivity.Model.Activity> Activitys
        {
            get { return activitys; }
            set
            {
                this.SetProperty(ref this.activitys, value);
            }
        }

        private string titleInput;

        public string TitleInput{
            get { return titleInput; }
            set
            {
                this.SetProperty(ref this.titleInput, value);
            }
        }

        private string contentInput;

        public string ContentInput{
            get { return contentInput; }
            set
            {
                this.SetProperty(ref this.contentInput, value);
            }
        }

        private string maxInput;

        public string MaxInput{
            get { return maxInput; }
            set
            {
                this.SetProperty(ref this.maxInput, value);
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
        public ActivityViewModel()
        {
            Init();
            refreshCommand = new DelegateCommand(RefreshCheck);
            joinCommand = new DelegateCommand(JoinCheck);
        }

        public ActivityViewModel(Activity a)
        {
            AboutActivity(a);
            joinCommand = new DelegateCommand(JoinCheck);
            ac = a;
        }

        public ActivityViewModel(int a)
        {
            activitys = new ObservableCollection<Activity>();
            SearchCheck();
        }
        #endregion  

        #region private method
        private void Init()
        {
            activitys = new ObservableCollection<Activity>();
            RefreshCheck();
        }

        private async void RefreshCheck()
        {
            StatuVisibile = Visibility.Visible;
            StatuText = "DOWNLOADING ACTIVITYS ......";
            LoginInput = "";
            List<KeyValuePair<string, string>> parmList = new List<KeyValuePair<string, string>>();
            string responseText = await App.httpHelper.DoPost("/activity/listActivity.do", parmList);
            JObject jo = JObject.Parse(responseText);
            string errcode = jo["msg"]["errcode"].ToString();
            string errmsg = jo["msg"]["errmsg"].ToString();
            if("1".Equals(errcode)){
                JArray ja = JArray.Parse(jo["activityList"].ToString());
                for (int i = 0; i < ja.Count; i++)
                {
                    Activity activity = new Activity();
                    activity.id =Int32.Parse(ja[i]["id"].ToString());
                    activity.title = ja[i]["title"].ToString();
                    activity.content = ja[i]["content"].ToString();
                    activity.max = Int32.Parse(ja[i]["max"].ToString());
                    List<KeyValuePair<string, string>> paramList = new List<KeyValuePair<string, string>>();
                    paramList.Add(new KeyValuePair<string, string>("id", activity.id.ToString()));
                    string respon = await App.httpHelper.DoPost("/r_user_activity/check.do", paramList);
                    //JObject statuJo = JObject.Parse(respon);
                    //string errcode = ((JObject)JObject.Parse(respon)["msg"])["errcode"].ToString();

                    if ("1".Equals(((JObject)JObject.Parse(respon)["msg"])["errcode"].ToString()))
                        activity.joinStatu = "已参加";
                    else
                        activity.joinStatu = "未参加";
                    activitys.Add(activity);
                }
            }
            StatuVisibile = Visibility.Collapsed;
        }

        private async void AboutActivity(Activity a){
            StatuVisibile = Visibility.Visible;
            StatuText = "DOWNLOADING ACTIVITYS ......";
            List<KeyValuePair<string, string>> parmList = new List<KeyValuePair<string, string>>();
            parmList.Add(new KeyValuePair<string, string>("id",a.id.ToString()));
            string responseText = await App.httpHelper.DoPost("/activity/find.do", parmList);
            JObject jo = JObject.Parse(responseText);
            TitleInput = jo["title"].ToString();
            ContentInput = jo["content"].ToString();
            MaxInput = jo["max"].ToString();
            StatuVisibile = Visibility.Collapsed;
        }

        private async Task<bool> CheckCheck(int aid)
        {
            bool res = false;
            List<KeyValuePair<string, string>> paramList = new List<KeyValuePair<string, string>>();
            paramList.Add(new KeyValuePair<string, string>("id", aid.ToString()));
            string responseText = await App.httpHelper.DoPost("/r_user_activity/check.do", paramList);
            JObject jo = JObject.Parse(responseText);
            if ("1".Equals(jo["errcode"].ToString()))
                res = true;
            return res;
        }

        private async void JoinCheck()
        {
            StatuVisibile = Visibility.Visible;
            StatuText = "JOINING ACTIVITYS ......";
            List<KeyValuePair<string, string>> parmList = new List<KeyValuePair<string, string>>();
            parmList.Add(new KeyValuePair<string, string>("id", ac.id.ToString()));
            parmList.Add(new KeyValuePair<string, string>("content", ac.content));
            parmList.Add(new KeyValuePair<string, string>("title", ac.title));
            string responseText = await App.httpHelper.DoPost("/r_user_activity/join.do", parmList);
            JObject jo = JObject.Parse(responseText);
            string errcode = ((JObject)jo["msg"])["errcode"].ToString();
            string errmsg = ((JObject)jo["msg"])["errmsg"].ToString();
            if(!"1".Equals(errcode)){
                LoginInput = errmsg;
            }
            else
            {
                LoginInput = "Join Successed!";
            }
            StatuVisibile = Visibility.Collapsed;
        }

        private async void SearchCheck()
        {
            activitys.Clear();
            StatuVisibile = Visibility.Visible;
            StatuText = "DOWNLOADING ACTIVITYS ......";
            LoginInput = "";
            List<KeyValuePair<string, string>> parmList = new List<KeyValuePair<string, string>>();
            parmList.Add(new KeyValuePair<string,string>("id",App.user.id.ToString()));
            string responseText = await App.httpHelper.DoPost("/r_user_activity/search.do", parmList);
            JObject jo = JObject.Parse(responseText);
            string errcode = jo["msg"]["errcode"].ToString();
            string errmsg = jo["msg"]["errmsg"].ToString();
            if ("1".Equals(errcode))
            {
                JArray ja = JArray.Parse(jo["activityList"].ToString());
                for (int i = 0; i < ja.Count; i++)
                {
                    Activity activity = new Activity();
                    activity.id = Int32.Parse(ja[i]["id"].ToString());
                    activity.title = ja[i]["title"].ToString();
                    activity.content = ja[i]["content"].ToString();
                    activity.max = Int32.Parse(ja[i]["max"].ToString());
                    activitys.Add(activity);
                }
            }
            StatuVisibile = Visibility.Collapsed;
        }
        #endregion

    }
}

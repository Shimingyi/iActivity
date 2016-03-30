using iActivity.ViewModel;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Runtime.InteropServices.WindowsRuntime;
using Windows.Foundation;
using Windows.Foundation.Collections;
using Windows.Phone.UI.Input;
using Windows.UI.ViewManagement;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Controls.Primitives;
using Windows.UI.Xaml.Data;
using Windows.UI.Xaml.Input;
using Windows.UI.Xaml.Media;
using Windows.UI.Xaml.Navigation;

// “空白页”项模板在 http://go.microsoft.com/fwlink/?LinkID=390556 上有介绍

namespace iActivity.View
{
    /// <summary>
    /// 可用于自身或导航至 Frame 内部的空白页。
    /// </summary>
    public sealed partial class Index : Page
    {
        UserViewModel userViewModel;
        ActivityViewModel activityViewModel;
        Frame rootFrame;
        bool _isInSide = false;
        TranslateTransform transfer = new TranslateTransform();
        double _oriXPosition = 0;
        Windows.Storage.ApplicationDataContainer _localSettings = Windows.Storage.ApplicationData.Current.LocalSettings;

        public Index()
        {
            this.InitializeComponent();
            Init();
            this.NavigationCacheMode = NavigationCacheMode.Required;
            rootFrame = Window.Current.Content as Frame;
            ContentGrid.ManipulationDelta += ContentGrid_ManipulationDelta;
            ContentGrid.ManipulationStarted += ContentGrid_ManipulationStarted;
            transfer = new TranslateTransform();
            ContentGrid.RenderTransform = this.transfer;
        }

        protected override void OnNavigatedTo(NavigationEventArgs e)
        {
            HardwareButtons.BackPressed += HardwareButtons_BackPressed;
            Change_StatuBar("", 0);
            int statu = (Int32)e.Parameter;
            if(statu == 1){
                NameShow.Text = _localSettings.Values["nickname"].ToString();
            }
        }

        protected override void OnNavigatedFrom(NavigationEventArgs e)
        {
            HardwareButtons.BackPressed -= HardwareButtons_BackPressed;
            if (_isInSide)
            {
                SideOutStory.Begin();
                HamOutStory.Begin();
                _isInSide = false;
                return;
            }
        }

        private void HardwareButtons_BackPressed(object sender, BackPressedEventArgs e)
        {
            if (_isInSide)
            {
                e.Handled = true;
                SideOutStory.Begin();
                HamOutStory.Begin();
                _isInSide = false;
                return;
            }
        }

        private async void Init()
        {
            userViewModel = new UserViewModel();
            this.DataContext = userViewModel;
            if (_localSettings.Values.ContainsKey("isRemember"))
            {
                if(_localSettings.Values["isRemember"].ToString() == "true"){
                    await userViewModel.AutoLogin();
                    NameShow.Text = userViewModel.User.nickname;
                }
            }
            activityViewModel = new ActivityViewModel();
            this.DataContext = activityViewModel;
        }

        #region Manipulation
        private void ContentGrid_ManipulationStarted(object sender, ManipulationStartedRoutedEventArgs e)
        {
            _oriXPosition = e.Position.X;
        }

        private void ContentGrid_ManipulationDelta(object sender, ManipulationDeltaRoutedEventArgs e)
        {
            if (e.Delta.Translation.X > 10 && _oriXPosition <= 10)
            {
                if (!_isInSide)
                {
                    SideInStory.Begin();
                    HamInStory.Begin();
                    _isInSide = true;
                }
            }
            else if (e.Delta.Translation.X < -10)
            {
                if (_isInSide)
                {
                    SideOutStory.Begin();
                    HamOutStory.Begin();
                    _isInSide = false;
                }
            }
        }
        #endregion

        #region About page view
        private async void Change_StatuBar(string str, int process)
        {
            StatusBar statusBar = StatusBar.GetForCurrentView();
            statusBar.BackgroundColor = Windows.UI.Color.FromArgb(1,67,197,144);
            statusBar.ForegroundColor = Windows.UI.Colors.White;
            statusBar.BackgroundOpacity = 1;
            statusBar.ProgressIndicator.Text = str;
            statusBar.ProgressIndicator.ProgressValue = process;
            await statusBar.ProgressIndicator.ShowAsync();
        }
        #endregion

        #region Click event
        private void ToProfileClick(object sender, RoutedEventArgs e)
        {
            this.rootFrame.Navigate(typeof(iActivity.View.Profile));
        }

        private void ToResumeClick(object sender, RoutedEventArgs e)
        {
            this.rootFrame.Navigate(typeof(iActivity.View.Resume));
        }

        private void ToActivityClick(object sender, RoutedEventArgs e)
        {
            this.rootFrame.Navigate(typeof(iActivity.View.Joined));
        }

        private void ToLoginClick(object sender, RoutedEventArgs e)
        {
            this.rootFrame.Navigate(typeof(iActivity.View.Login));
        }

        private void ToActivityClick(object sender, TappedRoutedEventArgs e)
        {
            Grid grid = sender as Grid;
            iActivity.Model.Activity a = grid.DataContext as iActivity.Model.Activity;
            iActivity.Units.NavigationHelper.Navigation(typeof(View.Activity),a);
        }

        private void ToSettingClick(object sender, RoutedEventArgs e)
        {

        }

        private void ToAboutClick(object sender, RoutedEventArgs e)
        {

        }

        private void HamburgerClick(object sender, RoutedEventArgs e)
        {
            if (_isInSide)
            {
                SideOutStory.Begin();
                HamOutStory.Begin();
                _isInSide = false;
            }
            else
            {
                SideInStory.Begin();
                HamInStory.Begin();
                _isInSide = true;
            }
        }


        private void TapTranBorder(object sender, RoutedEventArgs e)
        {
            if (_isInSide)
            {
                SideOutStory.Begin();
                HamOutStory.Begin();
                _isInSide = false;
                return;
            }
        }
        #endregion

       
        
    }
}

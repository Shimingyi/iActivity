using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Runtime.InteropServices.WindowsRuntime;
using System.Threading.Tasks;
using Windows.Foundation;
using Windows.Foundation.Collections;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Controls.Primitives;
using Windows.UI.Xaml.Data;
using Windows.UI.Xaml.Input;
using Windows.UI.Xaml.Media;
using Windows.UI.Xaml.Navigation;

// “空白页”项模板在 http://go.microsoft.com/fwlink/?LinkId=234238 上提供

namespace iActivity
{
    /// <summary>
    /// 可独立使用或用于导航至 Frame 内部的空白页。
    /// </summary>
    public sealed partial class MainPage : Page
    {
        int count;
        public MainPage()
        {
            this.InitializeComponent();
        }

        private void StartRequest(object sender, RoutedEventArgs e)
        {
            Task[] tasks = new Task[20];
            for (int i = 0; i < 20; i++)
            {
               tasks[i] = new Task(() =>
               {
                   DoTaoBao();
               });
                tasks[i].Start();
            }
         }

        public async void DoTaoBao()
        {
            
                string result = await App.httpHelper.DoGet("https://taobao.com");
                count++;
                ResultShow.Text = count.ToString();
            
        }

    }
}

using System;
using System.Collections.Generic;
using System.Text;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;

namespace iActivity.Units
{
    public class NavigationHelper
    {

        public static void Navigation(Type sourcePageType, Object obj)
        {
            Frame rootFrame = Window.Current.Content as Frame;
            rootFrame.Navigate(sourcePageType, obj);
        }

    }
}

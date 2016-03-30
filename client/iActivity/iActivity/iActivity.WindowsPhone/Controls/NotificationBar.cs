﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.InteropServices.WindowsRuntime;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Data;
using Windows.UI.Xaml.Documents;
using Windows.UI.Xaml.Input;
using Windows.UI.Xaml.Media;
using Windows.UI.Xaml.Media.Animation;

// “用户控件”项模板在 http://go.microsoft.com/fwlink/?LinkId=234235 上有介绍

namespace iActivity.Controls
{
    public sealed class NotificationBar : Control
    {
        private TextBlock notifyBlock;
        private Grid mainGrid;
        private Storyboard storyBoard;

        public NotificationBar()
        {
            this.DefaultStyleKey = typeof(NotificationBar);
        }

        private void GetTextBlockControl()
        {
            if (this.notifyBlock == null)
            {
                this.notifyBlock = this.GetTemplateChild("tb_Notify") as TextBlock;
            }
        }

        private void GetStoryBoardControl(string name)
        {
            if (this.storyBoard == null)
            {
                this.storyBoard = this.GetTemplateChild(name) as Storyboard;
            }
        }

        public void ShowMessage(string message)
        {
            GetTextBlockControl();
            GetStoryBoardControl("tb_Notify_in");
            if (notifyBlock != null && storyBoard != null)
            {
                notifyBlock.Text = message;
                storyBoard.Begin();
            }
        }
    }
}

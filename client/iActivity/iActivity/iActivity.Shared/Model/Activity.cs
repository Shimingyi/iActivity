using System;
using System.Collections.Generic;
using System.Text;

namespace iActivity.Model
{
    public class Activity
    {

        public int id { get; set; }

        public DateTime begintime { get; set; }

        public DateTime endtime { get; set; }

        public string content { get; set; }

        public string imgpath { get; set; }

        public int max { get; set; }

        public string title { get; set; }

        public int mid { get; set; }

        public string joinStatu { get; set; }
    }
}

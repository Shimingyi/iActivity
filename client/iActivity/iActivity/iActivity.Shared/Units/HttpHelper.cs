using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;
using Windows.Web.Http;

namespace iActivity.Units
{
    public class HttpHelper
    {

        private HttpClient httpclient;
        private string Host = "http://localhost:8080/Activity";

        public HttpHelper()
        {
            httpclient = new HttpClient();
            httpclient.DefaultRequestHeaders.Add("user-agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/36.0.1985.143 Safari/537.36");
            httpclient.DefaultRequestHeaders.Add("enctype", "multipart/form-data");
        }

        public async Task<string> DoGet(string uri)
        {
            string responseText = string.Empty;
            HttpResponseMessage response = await httpclient.GetAsync(new Uri(Host + uri));
            responseText = await response.Content.ReadAsStringAsync();
            return responseText;
        }

        public async Task<string> DoPost(string uri, List<KeyValuePair<string, string>> postContent)
        {
            string responseText = string.Empty;
            HttpResponseMessage response = await httpclient.PostAsync(new Uri(Host + uri), new HttpFormUrlEncodedContent(postContent));
            responseText = await response.Content.ReadAsStringAsync();
            return responseText;
        }


    }
}

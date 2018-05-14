using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Demo.Common
{

    /// <summary>
    ///wxmessage 的摘要说明
    /// </summary>
    public class wxmessage
    {
        public wxmessage()
        {
            //
            //TODO: 在此处添加构造函数逻辑

            //

        }
        public string FromUserName { get; set; }
        public string ToUserName { get; set; }
        public string MsgType { get; set; }
        public string EventName { get; set; }
        public string Content { get; set; }
        public string EventKey { get; set; }
    }
}

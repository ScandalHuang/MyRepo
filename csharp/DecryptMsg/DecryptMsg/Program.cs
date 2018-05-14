using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Xml;
using DecryptMsg;
namespace DecryptMsg
{
    class Program
    {
        static void Main(string[] args)
        {
            string xmldata = "<xml><ToUserName><![CDATA[toUser]]></ToUserName><FromUserName><![CDATA[fromUser]]></FromUserName><CreateTime>1348831860</CreateTime><MsgType><![CDATA[image]]></MsgType><Image><MediaId><![CDATA[media_id]]></MediaId></Image></xml>";
            XmlDocument doc = new XmlDocument();
            doc.LoadXml(xmldata);
            XmlNode root = doc.DocumentElement;
            string UserID = root.SelectSingleNode("FromUserName").InnerText;
            string CorpID = root.SelectSingleNode("ToUserName").InnerText;
            string createtime = root.SelectSingleNode("CreateTime").InnerText;
            System.DateTime startTime = TimeZone.CurrentTimeZone.ToLocalTime(new System.DateTime(1970, 1, 1));
            string time = ((int)(DateTime.Now - startTime).TotalSeconds).ToString();
            Console.Write("UserID:" + UserID + "\nCorpID:" + CorpID + "\nCreateTime:" +time);
            Console.ReadKey();
        }
        public int ConvertDateTimeInt(System.DateTime time)
        {
            System.DateTime startTime = TimeZone.CurrentTimeZone.ToLocalTime(new System.DateTime(1970, 1, 1));
            return (int)(time - startTime).TotalSeconds;
        }
    }
}

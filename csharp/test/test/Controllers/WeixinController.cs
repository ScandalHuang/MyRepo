using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using test.Models;
using Tencent;
using System.Web;
using System.Collections;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using System.Xml;
using System.IO;
using System.Text;

namespace test.Controllers
{
    public class WeixinController : ApiController
    {
        public HttpResponse Get([FromUri]UrldecodeModel model)
        {

            string sToken = "HON82gwUh3jIu";
            string sCorpID = "wwaaa6a2c1d43426a6";
            string sEncodingAESKey = "cBQP0uSzY26amPKGUjYMqoqsz7VHPU6HSOQkDGorVlM";

            WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(sToken, sEncodingAESKey, sCorpID);
            string sVerifyMsgSig = HttpUtility.UrlDecode(model.msg_signature);
            string sVerifyTimeStamp = HttpUtility.UrlDecode(model.timestamp);
            string sVerifyNonce = HttpUtility.UrlDecode(model.nonce);
            string sVerifyEchoStr = HttpUtility.UrlDecode(model.echostr);


            int ret = 0;
            string sEchoStr = string.Empty;
            ret = wxcpt.VerifyURL(sVerifyMsgSig, sVerifyTimeStamp, sVerifyNonce, sVerifyEchoStr, ref sEchoStr);
            if (ret != 0)
            {
                System.Console.WriteLine("ERR: VerifyURL fail, ret: " + ret);
            }
            HttpContext.Current.Response.Clear();
            HttpContext.Current.Response.Write(sEchoStr);
            HttpContext.Current.Response.End();
            return HttpContext.Current.Response;
                
        }
        public HttpResponse Post([FromUri]UrldecodeModel model, [FromBody]string postData)
        {

            string sToken = "HON82gwUh3jIu";
            string sCorpID = "wwaaa6a2c1d43426a6";
            string sEncodingAESKey = "cBQP0uSzY26amPKGUjYMqoqsz7VHPU6HSOQkDGorVlM";

            string sVerifyMsgSig = HttpUtility.UrlDecode(model.msg_signature);
            string sVerifyTimeStamp = HttpUtility.UrlDecode(model.timestamp);
            string sVerifyNonce = HttpUtility.UrlDecode(model.nonce);



            int ret = 0;
            string xmlData = string.Empty;

            #region 日志
            FileInfo fi = new FileInfo(@"c:\test\log.txt");
            using (FileStream fs = fi.Open(FileMode.OpenOrCreate, FileAccess.Write))
            {
                using (StreamWriter sw = new StreamWriter(fs, Encoding.UTF8))
                {
                    sw.Write("postData:\r\n" + postData + "\r\nsVerifyMsgSig:\r\n" + sVerifyMsgSig + "\r\nsVerifyTimeStamp:\r\n" + sVerifyTimeStamp + "\r\nsVerifyNonce:\r\n" + sVerifyNonce);
                }
            }
            #endregion

            WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(sToken, sEncodingAESKey, sCorpID);

            #region 日志
            try
            {
                
                ret = wxcpt.DecryptMsg(sVerifyMsgSig, sVerifyTimeStamp, sVerifyNonce, postData, ref xmlData);
                using (FileStream fs = fi.Open(FileMode.Append, FileAccess.Write))
                {
                    using (StreamWriter sw = new StreamWriter(fs, Encoding.UTF8))
                    {
                        sw.Write("\r\nret:\r\n"+ret+"\r\nxmlData:\r\n" + xmlData);
                    }
                }
            }
            catch (Exception e)
            {
                using (FileStream fs = fi.Open(FileMode.Append, FileAccess.Write))
                {
                    using (StreamWriter sw = new StreamWriter(fs, Encoding.UTF8))
                    {
                        sw.Write("\r\nexception:\r\n" + e);
                    }
                }
            }
            #endregion

            if (ret != 0)
            {
                System.Console.WriteLine("ERR: VerifyURL fail, ret: " + ret);
            }

            string UserID = string.Empty;
            string CorpID = string.Empty;

            #region 日志
            try
            {
                XmlDocument doc = new XmlDocument();
                doc.LoadXml(xmlData);
                XmlNode root = doc.DocumentElement;
                UserID = root.SelectSingleNode("FromUserName").InnerText;
                CorpID = root.SelectSingleNode("ToUserName").InnerText;
                using (FileStream fs = fi.Open(FileMode.Append, FileAccess.Write))
                {
                    using (StreamWriter sw = new StreamWriter(fs, Encoding.UTF8))
                    {
                        sw.Write("\r\nUserID:\r\n" + UserID + "\r\nCorpID:\r\n" + CorpID);
                    }
                }
            }
            catch (Exception e)
            {
                using (FileStream fs = fi.Open(FileMode.Append, FileAccess.Write))
                {
                    using (StreamWriter sw = new StreamWriter(fs, Encoding.UTF8))
                    {
                        sw.Write("\r\nexception:\r\n" + e);
                    }
                }
            }
            #endregion

            string createtime = ConvertDateTimeInt(DateTime.Now).ToString();
            string content = "Hello World!\nThis is a text from menu-clicked-auto-replied program!";
            string sResponseData = string.Format(
                "<xml><ToUserName><![CDATA[{0}]]></ToUserName><FromUserName><![CDATA[{1}]]></FromUserName>" +
                " <CreateTime>{2}</CreateTime><MsgType><![CDATA[text]]></MsgType>" +
                "<Content><![CDATA[{3}]]></Content></xml>", UserID, CorpID,createtime, content);


            string utf8sResponseData = Encoding.UTF8.GetString(Encoding.Default.GetBytes(sResponseData));
            string sEncryyptMsg = string.Empty;
            wxcpt.EncryptMsg(utf8sResponseData, createtime, sVerifyNonce, ref sEncryyptMsg);
            string utf8sEncryyptMsg = Encoding.UTF8.GetString(Encoding.Default.GetBytes(sEncryyptMsg));

            #region 日志
            using (FileStream fs = fi.Open(FileMode.Append, FileAccess.Write))
            {
                using (StreamWriter sw = new StreamWriter(fs, Encoding.UTF8))
                {
                    sw.Write("\r\nsResponseData:\r\n" + sResponseData + "\r\nsEncryyptMsg:\r\n" + sEncryyptMsg+
                        "\r\nutf8sResponseData:\r\n" + utf8sResponseData + "\r\nutf8sEncryyptMsg:\r\n" + utf8sEncryyptMsg);
                }
            }
            #endregion

            HttpContext.Current.Response.Clear();
            HttpContext.Current.Response.Write(utf8sEncryyptMsg);
            HttpContext.Current.Response.End();

            return HttpContext.Current.Response;
        }

        private int ConvertDateTimeInt(System.DateTime time)
        {
            System.DateTime startTime = TimeZone.CurrentTimeZone.ToLocalTime(new System.DateTime(1970, 1, 1));
            return (int)(time - startTime).TotalSeconds;
        }
    }
}

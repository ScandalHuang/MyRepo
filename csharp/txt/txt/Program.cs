using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Xml;
using Tencent;
namespace txt
{
    class Program
    {
        static void Main(string[] args)
        {
            //string xmlData = "<xml><ToUserName><![CDATA[wwaaa6a2c1d43426a6]]></ToUserName><FromUserName><![CDATA[wenfeng.huang]]></FromUserName><CreateTime>1525662954</CreateTime><MsgType><![CDATA[event]]></MsgType><AgentID>1000005</AgentID><Event><![CDATA[click]]></Event><EventKey><![CDATA[Punch]]></EventKey></xml>";
            //XmlDocument doc = new XmlDocument();
            //doc.LoadXml(xmlData);
            //XmlNode root = doc.DocumentElement;
            //string UserID = root.SelectSingleNode("FromUserName").InnerText;
            //string CorpID = root.SelectSingleNode("ToUserName").InnerText;
            //Console.Write("FromUserName:" + UserID + "\nToUserName:" + CorpID);
            //Console.Read();
            string msg="95f1f2a9de82ddde4b362787cba0a0e20bb9dd06";
            string timestamp="1525672167";
            string nonce="1524697125";
            string sToken = "HON82gwUh3jIu";
            string sCorpID = "wwaaa6a2c1d43426a6";
            string sEncodingAESKey = "cBQP0uSzY26amPKGUjYMqoqsz7VHPU6HSOQkDGorVlM";
            string en = "<xml><Encrypt><![CDATA[zLN3B+L7srIeHg5v8Q8w0RCqafmpHXLzWFn4OSozjGegiF2ndbkZbvZFbjvE1cGIEfw0n7mNULDPPKxWCTH11c+uKbCZLFnhEA1hzs/uL1OlboBZXy1kvVW5egOC/QMZF5SNqjh5lQHDollGdPTfzQfCzpvu940EAgAveF/nuEs=]]></Encrypt><MsgSignature><![CDATA[95f1f2a9de82ddde4b362787cba0a0e20bb9dd06]]></MsgSignature><TimeStamp><![CDATA[1525672167]]></TimeStamp><Nonce><![CDATA[1524697125]]></Nonce></xml>";
            WXBizMsgCrypt wxcp = new WXBizMsgCrypt(sToken, sEncodingAESKey, sCorpID);
            string xmldata = string.Empty;
            wxcp.DecryptMsg(msg, timestamp, nonce, en, ref xmldata);
            Console.Write("xmldata:" + xmldata);
            Console.Read();
        }
    }
}

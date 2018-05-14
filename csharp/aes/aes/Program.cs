using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Tencent;
using System.Web;

namespace aes
{
    class Program
    {
        static void Main(string[] args)
        {   
            string sToken = "6GPQsoax9yeWXRmA4siFFeVyojAn3LY";
            string sCorpID = "wwaaa6a2c1d43426a6";
            string sEncodingAESKey = "jAV8dy8cM6BsAjiXsUsTZ4vpHbvKN58Q5LeVLTjgeHe";

            WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(sToken, sEncodingAESKey, sCorpID);
            string sVerifyMsgSig = System.Web.HttpUtility.UrlDecode("HNYJRDpKj8Yq3wLw");
            string sVerifyTimeStamp = System.Web.HttpUtility.UrlDecode("1524885497");
            string sVerifyNonce = System.Web.HttpUtility.UrlDecode("c2b6ada60ed292cae21fab8d6c17da14a42f8c33");
            string sVerifyEchoStr = System.Web.HttpUtility.UrlDecode("rhPpD8WyDZfAUQT%2F3Kr5b3EAApDfSncTowFNz06yB7HMGcFAwW%2BMk48U2eduOFLBJ2AW8uEuYBoANWl8LpInWA%3D%3D",Encoding.UTF8);
            int ret = 0;
            string sEchoStr = String.Empty;
            ret = wxcpt.VerifyURL(sVerifyMsgSig, sVerifyTimeStamp, sVerifyNonce, sVerifyEchoStr, ref sEchoStr);
            if (ret != 0)
            {
                System.Console.WriteLine("ERR: VerifyURL fail, ret: " + ret);
                Console.WriteLine(ret);
                Console.WriteLine(sEchoStr);
                Console.Read();
            }
            else
            {
                Console.WriteLine(sEchoStr);
                Console.Read();
            }
        }
    }
}

using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace test.Models
{
    public class UrldecodeModel
    {
        public string echostr { get; set; }
        public string msg_signature { get; set; }
        public string timestamp { get; set; }
        public string nonce { get; set; }
    }
}
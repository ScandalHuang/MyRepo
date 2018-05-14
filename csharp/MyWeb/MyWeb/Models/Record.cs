using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace MyWeb.Models
{
    public class Record
    {
        [Key]
        public string CARD_ID { get; set; }
        public string PDATE { get; set; }
        public string PFIRST { get; set; }
        public string PLAST { get; set; }
        public string EMPWNO { get; set; }
        public string EMPCNM { get; set; }
        public string DEPNAM { get; set; }
        public string DEPTCNM { get; set; }
    }
}
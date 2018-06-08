using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.ComponentModel.DataAnnotations;

namespace TodoList.Models
{
    public class item
    {

        [Key]
        public string id { get; set; }
        public string month { get; set; }
        public string day { get; set; }
        public string remindTime { get; set; }
        public string machine { get; set; }
        public string note { get; set; }
    }
}       
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using System.Web.Http;
using MyWeb.Models;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using System.Web.Http.Cors;

namespace MyWeb.Controllers
{
    //[EnableCors("*","*","*")]
    public class RecordController : ApiController
    {
        private RecordDbContext db = new RecordDbContext();
        public string Get()
        {
            var Records = db.records.ToList();
            var json = JsonConvert.SerializeObject(Records);
            return json;
        }
        public string Post([FromBody]Time time)
        {
            var start=time.startDate;
            var end= time.endDate;
            var Records = db.records.Where(r => r.PDATE.CompareTo(start) > 0 && r.PDATE.CompareTo(end) < 0);
            var json = JsonConvert.SerializeObject(Records);
            return json ;
        }
    }
}

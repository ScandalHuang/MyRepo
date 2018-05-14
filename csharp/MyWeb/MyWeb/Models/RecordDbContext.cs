using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Data.Entity;

namespace MyWeb.Models
{
    public class RecordDbContext:DbContext
    {
        public  RecordDbContext():base("name=OracleDbContext"){}
        public DbSet<Record> records{get;set;}
        protected override void OnModelCreating(DbModelBuilder modelBuilder)
    {
        modelBuilder.HasDefaultSchema("PEOPLESEARCH");
        modelBuilder.Entity<Record>().ToTable("TB_RECORD");
        Database.SetInitializer<RecordDbContext>(null);
        base.OnModelCreating(modelBuilder);
    }

    }
}
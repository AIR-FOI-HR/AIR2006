using DrinkUp.DAL.Entities;
using DrinkUp.Models.Common;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace DrinkUp.WebAPI.REST
{
    public class KorisnikREST
    {
        public int Id { get; set; }
        public string OIB { get; set; }
        public string Ime { get; set; }
        public string Prezime { get; set; }
        public string Email { get; set; }
        public string Spol { get; set; }
        public int UlogaId { get; set; }
        public bool Aktivan { get; set; }
    }
}

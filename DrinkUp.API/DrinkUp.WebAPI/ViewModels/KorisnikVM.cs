using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace DrinkUp.WebAPI.ViewModels
{
    public class KorisnikVM
    {
        public int? Id { get; set; }
        public string Lozinka { get; set; }
        public string OIB { get; set; }
        public string Ime { get; set; }
        public string Prezime { get; set; }
        public string Email { get; set; }
        public int Spol { get; set; }
        public int UlogaId { get; set; }
    }
}

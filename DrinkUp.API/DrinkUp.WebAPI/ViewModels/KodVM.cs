using DrinkUp.Models.Common;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace DrinkUp.WebAPI.ViewModels
{
    public class KodVM
    {
        public string Id { get; set; }
        public DateTime? DatumKreiranja { get; set; }

        public ICollection<IAktivacijaObjektaModel> AktivacijaObjekta { get; set; }
        public ICollection<IKorisnikAktivacija> KorisnikAktivacija { get; set; }
        public ICollection<IKorisnikReset> KorisnikReset { get; set; }
    }
}

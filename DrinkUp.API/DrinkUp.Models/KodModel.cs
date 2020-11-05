using DrinkUp.Models.Common;
using System;
using System.Collections.Generic;
using System.Text;

namespace DrinkUp.Models
{
    public class KodModel : IKodModel
    {
        public string Id { get; set; }
        public DateTime DatumKreiranja { get; set; }
        
        public ICollection<IAktivacijaObjektaModel> AktivacijaObjekta { get; set; }
        public ICollection<IKorisnikAktivacija> KorisnikAktivacija { get; set; }
        public ICollection<IKorisnikReset> KorisnikReset { get; set; }
    }
}

using System;
using System.Collections.Generic;
using System.Text;

namespace DrinkUp.Models.Common
{
    public interface IKodModel
    {
        string Id { get; set; }
        DateTime DatumKreiranja { get; set; }

        ICollection<IAktivacijaObjektaModel> AktivacijaObjekta { get; set; }
        ICollection<IKorisnikAktivacija> KorisnikAktivacija { get; set; }
        ICollection<IKorisnikReset> KorisnikReset { get; set; }
    }
}

using System;
using System.Collections.Generic;
using System.Text;

namespace DrinkUp.Models.Common
{
    public interface ITokenModel
    {
        string Id { get; set; }
        int PonudaId { get; set; }
        DateTime DatumKreiranja { get; set; }
        bool Iskoristen { get; set; }
        int KorisnikId { get; set; }
        byte[] QR { get; set; }

        IPonudaModel Ponuda { get; set; }
        IKorisnikModel Korisnik { get; set; }
    }
}

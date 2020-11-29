using DrinkUp.Models.Common;
using System;
using System.Collections.Generic;
using System.Text;

namespace DrinkUp.Models
{
    public class TokenModel : ITokenModel
    {
        public string Id { get; set; }
        public int PonudaId { get; set; }
        public DateTime DatumKreiranja { get; set; }
        public bool Iskoristen { get; set; }
        public int KorisnikId { get; set; }
        public byte[] QR { get; set; }

        public IPonudaModel Ponuda { get; set; }
        public IKorisnikModel Korisnik { get; set; }
    }
}

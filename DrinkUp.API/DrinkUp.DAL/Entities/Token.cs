using System;
using System.Collections.Generic;

namespace DrinkUp.DAL.Entities
{
    public partial class Token
    {
        public string Id { get; set; }
        public int PonudaId { get; set; }
        public DateTime DatumKreiranja { get; set; }
        public byte Iskoristen { get; set; }
        public int KorisnikId { get; set; }
        public byte[] Qr { get; set; }

        public virtual Korisnik Korisnik { get; set; }
        public virtual Ponuda Ponuda { get; set; }
    }
}

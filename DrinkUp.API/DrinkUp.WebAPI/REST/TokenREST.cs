using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace DrinkUp.WebAPI.REST
{
    public class TokenREST
    {
        public string Id { get; set; }
        public int PonudaId { get; set; }
        public DateTime DatumKreiranja { get; set; }
        public bool Iskoristen { get; set; }
        public int KorisnikId { get; set; }
        public byte[] QR { get; set; }
    }
}

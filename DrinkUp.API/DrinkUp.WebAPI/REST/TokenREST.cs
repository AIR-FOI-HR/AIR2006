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

        public PonudaREST Ponuda { get; set; }
        public ICollection<KorisnikTokenREST> KorisnikToken { get; set; }
    }
}

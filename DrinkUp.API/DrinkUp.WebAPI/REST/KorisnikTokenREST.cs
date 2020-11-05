using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace DrinkUp.WebAPI.REST
{
    public class KorisnikTokenREST
    {
        public int Id { get; set; }
        public int KorisnikId { get; set; }
        public string TokenId { get; set; }
    }
}

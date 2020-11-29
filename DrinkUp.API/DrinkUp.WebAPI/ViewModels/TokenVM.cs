using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace DrinkUp.WebAPI.ViewModels
{
    public class TokenVM
    {
        public string Id { get; set; }
        public int PonudaId { get; set; }
        public int KorisnikId { get; set; }
    }
}

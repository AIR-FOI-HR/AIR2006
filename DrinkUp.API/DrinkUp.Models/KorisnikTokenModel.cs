using DrinkUp.Models.Common;
using System;
using System.Collections.Generic;
using System.Text;

namespace DrinkUp.Models
{
    public class KorisnikTokenModel : IKorisnikTokenModel
    {
        public int Id { get; set; }
        public int KorisnikId { get; set; }
        public string TokenId { get; set; }
        
        public IKorisnikModel Korisnik { get; set; }
        public ITokenModel Token { get; set; }
    }
}

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
        
        public IPonudaModel Ponuda { get; set; }
        public ICollection<IKorisnikTokenModel> KorisnikToken { get; set; }
    }
}

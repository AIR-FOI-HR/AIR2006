using System;
using System.Collections.Generic;

namespace DrinkUp.DAL.Entities
{
    public partial class Token
    {
        public Token()
        {
            KorisnikToken = new HashSet<KorisnikToken>();
        }

        public string Id { get; set; }
        public int PonudaId { get; set; }

        public virtual Ponuda Ponuda { get; set; }
        public virtual ICollection<KorisnikToken> KorisnikToken { get; set; }
    }
}

using System;
using System.Collections.Generic;

namespace DrinkUp.DAL.Entities
{
    public partial class TokenEntity
    {
        public TokenEntity()
        {
            KorisnikToken = new HashSet<KorisnikTokenEntity>();
        }

        public string Id { get; set; }
        public int PonudaId { get; set; }

        public virtual PonudaEntity Ponuda { get; set; }
        public virtual ICollection<KorisnikTokenEntity> KorisnikToken { get; set; }
    }
}

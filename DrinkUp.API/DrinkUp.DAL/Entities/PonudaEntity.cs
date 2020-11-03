using System;
using System.Collections.Generic;

namespace DrinkUp.DAL.Entities
{
    public partial class PonudaEntity
    {
        public PonudaEntity()
        {
            ObjektPonuda = new HashSet<ObjektPonudaEntity>();
            Token = new HashSet<TokenEntity>();
        }

        public int Id { get; set; }
        public string Naslov { get; set; }
        public string Opis { get; set; }
        public double Cijena { get; set; }
        public int BrojTokena { get; set; }
        public int VrstaPonudeId { get; set; }

        public virtual VrstaPonudeEntity VrstaPonude { get; set; }
        public virtual ICollection<ObjektPonudaEntity> ObjektPonuda { get; set; }
        public virtual ICollection<TokenEntity> Token { get; set; }
    }
}

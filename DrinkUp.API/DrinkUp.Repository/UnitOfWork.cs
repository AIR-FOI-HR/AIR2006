using DrinkUp.DAL.Context;
using DrinkUp.DAL.Entities;
using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;

namespace DrinkUp.Repository
{
    public class UnitOfWork : IDisposable
    {
        private DrinkUpContext context = new DrinkUpContext();
        private GenericRepository<KorisnikEntity> korisnikRepository;
        private GenericRepository<KorisnikTokenEntity> korisnikTokenRepository;
        private GenericRepository<ObjektEntity> objektRepository;
        private GenericRepository<ObjektPonudaEntity> objektPonudaRepository;
        private GenericRepository<PonudaEntity> ponudaRepository;
        private GenericRepository<TokenEntity> tokenRepository;
        private GenericRepository<UlogaEntity> ulogaRepository;
        private GenericRepository<VrstaPonudeEntity> vrstaPonudeRepository;
        private GenericRepository<ZaposlenikObjektEntity> zaposlenikObjektRepository;

        #region Repository Getters

        public GenericRepository<KorisnikEntity> KorisnikRepository
        {
            get
            {

                if (korisnikRepository == null)
                {
                    korisnikRepository = new GenericRepository<KorisnikEntity>(context);
                }
                return korisnikRepository;
            }
        }

        public GenericRepository<KorisnikTokenEntity> KorisnikTokenRepository
        {
            get
            {

                if (korisnikTokenRepository == null)
                {
                    korisnikTokenRepository = new GenericRepository<KorisnikTokenEntity>(context);
                }
                return korisnikTokenRepository;
            }
        }

        public GenericRepository<ObjektEntity> ObjektRepository
        {
            get
            {

                if (objektRepository == null)
                {
                    objektRepository = new GenericRepository<ObjektEntity>(context);
                }
                return objektRepository;
            }
        }

        public GenericRepository<ObjektPonudaEntity> ObjektPonudaRepository
        {
            get
            {

                if (objektPonudaRepository == null)
                {
                    objektPonudaRepository = new GenericRepository<ObjektPonudaEntity>(context);
                }
                return objektPonudaRepository;
            }
        }

        public GenericRepository<PonudaEntity> PonudaRepository
        {
            get
            {

                if (ponudaRepository == null)
                {
                    ponudaRepository = new GenericRepository<PonudaEntity>(context);
                }
                return ponudaRepository;
            }
        }

        public GenericRepository<TokenEntity> TokenRepository
        {
            get
            {

                if (tokenRepository == null)
                {
                    tokenRepository = new GenericRepository<TokenEntity>(context);
                }
                return tokenRepository;
            }
        }

        public GenericRepository<UlogaEntity> UlogaRepository
        {
            get
            {

                if (ulogaRepository == null)
                {
                    ulogaRepository = new GenericRepository<UlogaEntity>(context);
                }
                return ulogaRepository;
            }
        }

        public GenericRepository<VrstaPonudeEntity> VrstaPonudeRepository
        {
            get
            {

                if (vrstaPonudeRepository == null)
                {
                    vrstaPonudeRepository = new GenericRepository<VrstaPonudeEntity>(context);
                }
                return vrstaPonudeRepository;
            }
        }

        public GenericRepository<ZaposlenikObjektEntity> ZaposlenikObjektRepository
        {
            get
            {

                if (zaposlenikObjektRepository == null)
                {
                    zaposlenikObjektRepository = new GenericRepository<ZaposlenikObjektEntity>(context);
                }
                return zaposlenikObjektRepository;
            }
        }

        #endregion

        public async Task SaveAsync()
        {
            await context.SaveChangesAsync();
        }

        private bool disposed = false;

        protected virtual void Dispose(bool disposing)
        {
            if (!this.disposed)
            {
                if (disposing)
                {
                    context.Dispose();
                }
            }
            this.disposed = true;
        }

        public void Dispose()
        {
            Dispose(true);
            GC.SuppressFinalize(this);
        }
    }
}

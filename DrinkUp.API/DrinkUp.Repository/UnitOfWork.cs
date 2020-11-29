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
        private GenericRepository<Korisnik> korisnikRepository;
        private GenericRepository<KorisnikAktivacija> korisnikAktivacijaRepository;
        private GenericRepository<KorisnikReset> korisnikResetRepository;
        private GenericRepository<Kod> kodRepository;
        private GenericRepository<Objekt> objektRepository;
        private GenericRepository<Ponuda> ponudaRepository;
        private GenericRepository<Token> tokenRepository;
        private GenericRepository<Uloga> ulogaRepository;
        private GenericRepository<VrstaPonude> vrstaPonudeRepository;
        private GenericRepository<ZaposlenikObjekt> zaposlenikObjektRepository;

        #region Repository Getters

        public GenericRepository<Korisnik> KorisnikRepository
        {
            get
            {

                if (korisnikRepository == null)
                {
                    korisnikRepository = new GenericRepository<Korisnik>(context);
                }
                return korisnikRepository;
            }
        }

        public GenericRepository<KorisnikAktivacija> KorisnikAktivacijaRepository
        {
            get
            {

                if (korisnikAktivacijaRepository == null)
                {
                    korisnikAktivacijaRepository = new GenericRepository<KorisnikAktivacija>(context);
                }
                return korisnikAktivacijaRepository;
            }
        }

        public GenericRepository<KorisnikReset> KorisnikResetRepository
        {
            get
            {

                if (korisnikResetRepository == null)
                {
                    korisnikResetRepository = new GenericRepository<KorisnikReset>(context);
                }
                return korisnikResetRepository;
            }
        }

        public GenericRepository<Kod> KodRepository
        {
            get
            {

                if (kodRepository == null)
                {
                    kodRepository = new GenericRepository<Kod>(context);
                }
                return kodRepository;
            }
        }

        public GenericRepository<Objekt> ObjektRepository
        {
            get
            {

                if (objektRepository == null)
                {
                    objektRepository = new GenericRepository<Objekt>(context);
                }
                return objektRepository;
            }
        }

        public GenericRepository<Ponuda> PonudaRepository
        {
            get
            {

                if (ponudaRepository == null)
                {
                    ponudaRepository = new GenericRepository<Ponuda>(context);
                }
                return ponudaRepository;
            }
        }

        public GenericRepository<Token> TokenRepository
        {
            get
            {

                if (tokenRepository == null)
                {
                    tokenRepository = new GenericRepository<Token>(context);
                }
                return tokenRepository;
            }
        }

        public GenericRepository<Uloga> UlogaRepository
        {
            get
            {

                if (ulogaRepository == null)
                {
                    ulogaRepository = new GenericRepository<Uloga>(context);
                }
                return ulogaRepository;
            }
        }

        public GenericRepository<VrstaPonude> VrstaPonudeRepository
        {
            get
            {

                if (vrstaPonudeRepository == null)
                {
                    vrstaPonudeRepository = new GenericRepository<VrstaPonude>(context);
                }
                return vrstaPonudeRepository;
            }
        }

        public GenericRepository<ZaposlenikObjekt> ZaposlenikObjektRepository
        {
            get
            {

                if (zaposlenikObjektRepository == null)
                {
                    zaposlenikObjektRepository = new GenericRepository<ZaposlenikObjekt>(context);
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

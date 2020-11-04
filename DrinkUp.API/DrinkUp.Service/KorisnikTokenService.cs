using AutoMapper;
using DrinkUp.Common;
using DrinkUp.DAL.Entities;
using DrinkUp.Models.Common;
using DrinkUp.Repository;
using DrinkUp.Service.Common;
using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;

namespace DrinkUp.Service
{
    public class KorisnikTokenService : IKorisnikTokenService
    {
        protected GenericRepository<KorisnikTokenEntity> Repository { get; private set; }
        protected IMapper Mapper { get; private set; }
        private readonly UnitOfWork unitOfWork;

        public KorisnikTokenService(IMapper mapper)
        {
            unitOfWork = new UnitOfWork();
            Repository = unitOfWork.KorisnikTokenRepository;
            Mapper = mapper;
        }

        public async Task DeleteAsync(int id)
        {
            await Repository.DeleteAsync(id);
            await unitOfWork.SaveAsync();
        }

        public async Task<IEnumerable<IKorisnikTokenModel>> GetAsync(GetParams<IKorisnikTokenModel> getParams)
        {
            return Mapper.Map<ICollection<IKorisnikTokenModel>>(await Repository.Get(Mapper.Map<GetParams<KorisnikTokenEntity>>(getParams)));
        }

        public async Task<IKorisnikTokenModel> GetAsync(int id)
        {
            return Mapper.Map<IKorisnikTokenModel>(await Repository.GetByID(id));
        }

        public async Task InsertAsync(IKorisnikTokenModel entity)
        {
            Repository.Insert(Mapper.Map<KorisnikTokenEntity>(entity));
            await unitOfWork.SaveAsync();
        }

        public async Task UpdateAsync(IKorisnikTokenModel entity)
        {
            Repository.Update(Mapper.Map<KorisnikTokenEntity>(entity));
            await unitOfWork.SaveAsync();
        }
    }
}

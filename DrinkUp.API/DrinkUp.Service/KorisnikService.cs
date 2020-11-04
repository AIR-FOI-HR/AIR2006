using AutoMapper;
using DrinkUp.Common;
using DrinkUp.DAL.Entities;
using DrinkUp.Models.Common;
using DrinkUp.Repository;
using DrinkUp.Service.Common;
using System;
using System.Collections.Generic;
using System.Net.Http.Headers;
using System.Threading.Tasks;

namespace DrinkUp.Service
{
    public class KorisnikService : IKorisnikService
    {
        protected GenericRepository<KorisnikEntity> Repository { get; private set; }
        protected IMapper Mapper { get; private set; }
        private readonly UnitOfWork unitOfWork;

        public KorisnikService(IMapper mapper)
        {
            unitOfWork = new UnitOfWork();
            Repository = unitOfWork.KorisnikRepository;
            Mapper = mapper;
        }

        public async Task DeleteAsync(int id)
        {
            await Repository.DeleteAsync(id);
            await unitOfWork.SaveAsync();
        }

        public async Task<IEnumerable<IKorisnikModel>> GetAsync(GetParams<IKorisnikModel> getParams)
        {
            return Mapper.Map<ICollection<IKorisnikModel>>(await Repository.Get(Mapper.Map<GetParams<KorisnikEntity>>(getParams)));
        }

        public async Task<IKorisnikModel> GetAsync(int id)
        {
            return Mapper.Map<IKorisnikModel>(await Repository.GetByID(id));
        }

        public async Task InsertAsync(IKorisnikModel entity)
        {
            Repository.Insert(Mapper.Map<KorisnikEntity>(entity));
            await unitOfWork.SaveAsync();
        }

        public async Task UpdateAsync(IKorisnikModel entity)
        {
            Repository.Update(Mapper.Map<KorisnikEntity>(entity));
            await unitOfWork.SaveAsync();
        }
    }
}

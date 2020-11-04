﻿using AutoMapper;
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
    public class PonudaService : IPonudaService
    {
        protected GenericRepository<PonudaEntity> Repository { get; private set; }
        protected IMapper Mapper { get; private set; }
        private readonly UnitOfWork unitOfWork;

        public PonudaService(IMapper mapper)
        {
            unitOfWork = new UnitOfWork();
            Repository = unitOfWork.PonudaRepository;
            Mapper = mapper;
        }

        public async Task DeleteAsync(int id)
        {
            await Repository.DeleteAsync(id);
            await unitOfWork.SaveAsync();
        }

        public async Task<IEnumerable<IPonudaModel>> GetAsync(GetParams<IPonudaModel> getParams)
        {
            return Mapper.Map<ICollection<IPonudaModel>>(await Repository.Get(Mapper.Map<GetParams<PonudaEntity>>(getParams)));
        }

        public async Task<IPonudaModel> GetAsync(int id)
        {
            return Mapper.Map<IPonudaModel>(await Repository.GetByID(id));
        }

        public async Task InsertAsync(IPonudaModel entity)
        {
            Repository.Insert(Mapper.Map<PonudaEntity>(entity));
            await unitOfWork.SaveAsync();
        }

        public async Task UpdateAsync(IPonudaModel entity)
        {
            Repository.Update(Mapper.Map<PonudaEntity>(entity));
            await unitOfWork.SaveAsync();
        }
    }
}

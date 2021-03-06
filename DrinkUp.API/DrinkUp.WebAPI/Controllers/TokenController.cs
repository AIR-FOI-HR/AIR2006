﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Threading.Tasks;
using AutoMapper;
using DrinkUp.Common;
using DrinkUp.Common.Filter;
using DrinkUp.Common.Page;
using DrinkUp.Common.Sort;
using DrinkUp.Models;
using DrinkUp.Models.Common;
using DrinkUp.Service.Common;
using DrinkUp.WebAPI.REST;
using DrinkUp.WebAPI.ViewModels;
using Microsoft.AspNetCore.Mvc;

namespace DrinkUp.WebAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class TokenController : ControllerBase
    {
        public ITokenService Service { get; }
        public IMapper Mapper { get; }
        public IFilter Filter { get; }
        public ISort Sort { get; }
        public IPagedResult PagedResult { get; }

        public TokenController(ITokenService service, IMapper mapper, IFilter filter, ISort sort, IPagedResult pagedResult)
        {
            Service = service;
            Mapper = mapper;
            Filter = filter;
            Sort = sort;
            PagedResult = pagedResult;
        }

        [HttpGet]
        public async Task<ICollection<TokenREST>> GetAsync(string filterColumn = "",
            string filterValue = "", int filterOption = 3, string sortBy = "",
            int sortOrder = 1, int pageSize = 1000, int page = 1)
        {
            GetParams<TokenModel> getParams = new GetParams<TokenModel>()
            {
                PageNumber = page,
                PageSize = pageSize,
                Include = "Ponuda,Ponuda.Objekt"
            };
            FilterParams filterParams = new FilterParams()
            {
                ColumnName = filterColumn,
                FilterValue = filterValue,
                FilterOption = (FilterOptions)filterOption
            };
            FilterParams activeParam = new FilterParams()
            {
                ColumnName = "Iskoristen",
                FilterValue = "0",
                FilterOption = FilterOptions.IsEqualTo
            };
            SortingParams sortingParams = new SortingParams()
            {
                ColumnName = sortBy,
                SortOrder = (SortOrders)sortOrder
            };

            getParams.FilterParam = new[] { filterParams, activeParam };
            getParams.SortingParam = new[] { sortingParams };
            getParams.Filter = Filter;
            getParams.Sort = Sort;
            getParams.Page = PagedResult;

            return Mapper.Map<List<TokenREST>>(await Service.GetAsync(Mapper.Map<GetParams<ITokenModel>>(getParams)));
        }

        [HttpGet("activate/{id}")]
        public async Task<HttpResponseMessage> ActivateAsync(string id)
        {
            try
            {
                GetParams<TokenModel> getParams = new GetParams<TokenModel>()
                {
                    PageSize = 1000,
                    PageNumber = 1,
                    Filter = Filter,
                    Sort = Sort,
                    Page = PagedResult
                };

                await Service.ActivateAsync(id, Mapper.Map<GetParams<ITokenModel>>(getParams));
            }
            catch (Exception e)
            {
                return new HttpResponseMessage(HttpStatusCode.BadRequest);
            }
            return new HttpResponseMessage(HttpStatusCode.NoContent);
        }

        [HttpGet("{id}")]
        public async Task<TokenREST> GetAsync(string id)
        {
            return Mapper.Map<TokenREST>(await Service.GetAsync(id));
        }

        [HttpPost("add")]
        public async Task<byte[]> AddAsync(TokenVM token)
        {
            try
            {
                GetParams<TokenModel> getParams = new GetParams<TokenModel>()
                {
                    PageSize = 1000,
                    PageNumber = 1,
                    Filter = Filter,
                    Sort = Sort,
                    Page = PagedResult
                };
                return await Service.InsertAsync(Mapper.Map<TokenModel>(token), Mapper.Map<GetParams<ITokenModel>>(getParams));
            }
            catch (Exception)
            {
                return null;
            }
        }

        [HttpPut("update")]
        public async Task<HttpResponseMessage> UpdateAsync(TokenVM token)
        {
            try
            {
                await Service.UpdateAsync(Mapper.Map<TokenModel>(token));
            }
            catch
            {
                return new HttpResponseMessage(HttpStatusCode.BadRequest);
            }
            return new HttpResponseMessage(HttpStatusCode.NoContent);
        }

        [HttpDelete("delete/{id}")]
        public async Task<HttpResponseMessage> DeleteAsync(string id)
        {
            try
            {
                await Service.DeleteAsync(id);
            }
            catch
            {
                return new HttpResponseMessage(HttpStatusCode.BadRequest);
            }
            return new HttpResponseMessage(HttpStatusCode.NoContent);
        }
    }
}

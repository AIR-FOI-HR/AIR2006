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
    public class ObjektController : ControllerBase
    {
        public IObjektService Service { get; }
        public IMapper Mapper { get; }
        public IFilter Filter { get; }
        public ISort Sort { get; }
        public IPagedResult PagedResult { get; }

        public ObjektController(IObjektService service, IMapper mapper, IFilter filter, ISort sort, IPagedResult pagedResult)
        {
            Service = service;
            Mapper = mapper;
            Filter = filter;
            Sort = sort;
            PagedResult = pagedResult;
        }

        [HttpGet]
        public async Task<ICollection<ObjektREST>> GetAsync(string filterColumn = "",
            string filterValue = "", int filterOption = 3, string sortBy = "",
            int sortOrder = 1, int pageSize = 1000, int page = 1)
        {
            GetParams<ObjektModel> getParams = new GetParams<ObjektModel>()
            {
                PageNumber = page,
                PageSize = pageSize
            };
            FilterParams filterParams = new FilterParams()
            {
                ColumnName = filterColumn,
                FilterValue = filterValue,
                FilterOption = (FilterOptions)filterOption
            };
            SortingParams sortingParams = new SortingParams()
            {
                ColumnName = sortBy,
                SortOrder = (SortOrders)sortOrder
            };

            getParams.FilterParam = new[] { filterParams };
            getParams.SortingParam = new[] { sortingParams };
            getParams.Filter = Filter;
            getParams.Sort = Sort;
            getParams.Page = PagedResult;

            return Mapper.Map<List<ObjektREST>>(await Service.GetAsync(Mapper.Map<GetParams<IObjektModel>>(getParams)));
        }

        [HttpGet("{id}")]
        public async Task<ObjektREST> GetAsync(int id)
        {
            return Mapper.Map<ObjektREST>(await Service.GetAsync(id));
        }

        [HttpPost("add")]
        public async Task<HttpResponseMessage> AddAsync(ObjektVM uloga)
        {
            try
            {
                await Service.InsertAsync(Mapper.Map<ObjektModel>(uloga));
            }
            catch
            {
                return new HttpResponseMessage(HttpStatusCode.BadRequest);
            }
            return new HttpResponseMessage(HttpStatusCode.Created);
        }

        [HttpPut("update")]
        public async Task<HttpResponseMessage> UpdateAsync(ObjektVM uloga)
        {
            try
            {
                await Service.UpdateAsync(Mapper.Map<ObjektModel>(uloga));
            }
            catch
            {
                return new HttpResponseMessage(HttpStatusCode.BadRequest);
            }
            return new HttpResponseMessage(HttpStatusCode.NoContent);
        }

        [HttpDelete("delete/{id}")]
        public async Task<HttpResponseMessage> DeleteAsync(int id)
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

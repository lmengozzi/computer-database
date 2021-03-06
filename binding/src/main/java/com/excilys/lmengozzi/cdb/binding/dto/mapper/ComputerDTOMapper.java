package com.excilys.lmengozzi.cdb.binding.dto.mapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import com.excilys.lmengozzi.cdb.entity.Computer;
import com.excilys.lmengozzi.cdb.binding.dto.ComputerDTO;
import com.excilys.lmengozzi.cdb.validation.DateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class ComputerDTOMapper {
	@Autowired
	private DateValidator dateValidator;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private CompanyDTOMapper companyDTOMapper;

	public ComputerDTO toDTO(Computer computer) {
		if (computer == null) {
			return null;
		}
		String pattern = messageSource.getMessage("validation.date.format", null, LocaleContextHolder.getLocale());
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		ComputerDTO dto = new ComputerDTO();
		dto.setId(computer.getId());
		dto.setName(computer.getName());
		if (computer.getIntroduced() != null) {
			dto.setIntroduced(computer.getIntroduced().format(formatter));
		}
		if (computer.getDiscontinued() != null) {
			dto.setDiscontinued(computer.getDiscontinued().format(formatter));
		}
		if (computer.getCompany() != null) {
			dto.setCompanyDTO(companyDTOMapper.toDTO(computer.getCompany()));
		} else {
			dto.setCompanyDTO(null);
		}
		return dto;
	}

	public Computer toComputer(ComputerDTO dto) {
		if (dto == null) {
			return null;
		}

		String pattern = messageSource.getMessage("validation.date.format", null, LocaleContextHolder.getLocale());
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);

		Computer bean = Computer.builder().id(dto.getId()).name(dto.getName()).build();

		if (dto.getIntroduced() != null) {
			if (dateValidator.isValid(dto.getIntroduced())) {
				bean.setIntroduced(LocalDateTime.of(LocalDate.parse(dto.getIntroduced(), formatter), LocalTime.MIDNIGHT));
			} else {
				bean.setIntroduced(null);
			}
		} else {
			bean.setIntroduced(null);
		}
		if (dto.getDiscontinued() != null) {
			if (dateValidator.isValid(dto.getDiscontinued())) {
				bean.setDiscontinued(LocalDateTime.of(LocalDate.parse(dto.getDiscontinued(), formatter), LocalTime.MIDNIGHT));
			} else {
				bean.setDiscontinued(null);
			}
		} else {
			bean.setDiscontinued(null);
		}
		if (dto.getCompanyDTO().getId() != 0) {
			bean.setCompany(companyDTOMapper.toComputer(dto.getCompanyDTO()));
		} else {
			bean.setCompany(null);
		}
		return bean;
	}
}
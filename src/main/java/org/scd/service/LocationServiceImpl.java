package org.scd.service;

import org.scd.config.exception.BusinessException;
import org.scd.model.Location;
import org.scd.model.dto.LocationDTO;
import org.scd.model.security.CustomUserDetails;
import org.scd.repository.LocationRepository;
import org.scd.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;


@Service
public class LocationServiceImpl implements LocationService {

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Location addLocation(CustomUserDetails userPrincipal, LocationDTO locationDTO) throws BusinessException {
        if(Objects.isNull(locationDTO)){
            throw new BusinessException(401,"Body null !");
        }

        if(Objects.isNull(locationDTO.getLatitude())){
            throw new BusinessException(400,"Latitude cannot be null ! ");
        }

        if(Objects.isNull(locationDTO.getLongitude())){
            throw new BusinessException(400,"Longitude cannot be null !");
        }

        Location location = new Location();
        location.setUser(userPrincipal.getUser());
        location.setLatitude(Double.parseDouble(locationDTO.getLatitude()));
        location.setLongitude(Double.parseDouble(locationDTO.getLongitude()));
        location.setDate(new Date());

        locationRepository.save(location);

        return location;
    }

    @Override
    public Location findLocationById(CustomUserDetails userPrincipal, Long id) throws BusinessException {
        if(Objects.isNull(id)){
            throw new BusinessException(400,"ID cannot be null !");
        }

        Location location = locationRepository.findById(id).orElse(null);

        if(Objects.isNull(location)){
            throw new BusinessException(400,"Location not found !");
        }

        if(!location.getUser().getId().equals(userPrincipal.getUser().getId())) {
            throw new BusinessException(400,"Location not found !");
        }

        return location;
    }

    @Override
    public Location updateLocationById(CustomUserDetails userPrincipal, Long id, LocationDTO locationDTO) throws BusinessException {
        if(Objects.isNull(id)){
            throw new BusinessException(400,"ID cannot be null !");
        }

        Location location = locationRepository.findById(id).orElse(null);

        if(Objects.isNull(location)){
            throw new BusinessException(400,"Location not found !");
        }

        if(!location.getUser().getId().equals(userPrincipal.getUser().getId())) {
            throw new BusinessException(400,"Location not found !");
        }

        if(Objects.isNull(locationDTO)){
            throw new BusinessException(401,"Body null !");
        }

        if(Objects.isNull(locationDTO.getLatitude())){
            throw new BusinessException(400,"Latitude cannot be null ! ");
        }

        if(Objects.isNull(locationDTO.getLongitude())){
            throw new BusinessException(400,"Longitude cannot be null !");
        }

        location.setLatitude(Double.parseDouble(locationDTO.getLatitude()));
        location.setLongitude(Double.parseDouble(locationDTO.getLongitude()));
        location.setDate(new Date());

        locationRepository.save(location);

        return location;

    }

    @Override
    public void deleteLocationById(CustomUserDetails customUserDetails, Long id) throws BusinessException {

    }

    @Override
    public List<Location> getFilteredLocations(Long userId, Date startDate, Date endDate) throws BusinessException {
        return null;
    }
}

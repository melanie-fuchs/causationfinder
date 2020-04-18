package ch.yoursource.causationfinder.dto;

import java.util.ArrayList;
import java.util.List;

// Wrapper-class that contains an array, holding UpdateCustomParamterDto-objects. Class needed to successfully
// transfer data between html-page and controller
public class UpdateCustomParameterDtoListWrapper {
    List<UpdateCustomParameterDto> updateCustomParameterDtoList;
    
    public UpdateCustomParameterDtoListWrapper() {
        this.updateCustomParameterDtoList = new ArrayList<UpdateCustomParameterDto>();
    }
    
    public List<UpdateCustomParameterDto> getUpdateCustomParameterDtoList() {
        return updateCustomParameterDtoList;
    }
    
    public void setUpdateCustomParameterDtoList(List<UpdateCustomParameterDto> updateCustomParameterDtoList) {
        this.updateCustomParameterDtoList = updateCustomParameterDtoList;
    }
    
    public void add(UpdateCustomParameterDto updateCustomParameterDto) {
        this.updateCustomParameterDtoList.add(updateCustomParameterDto);
    }
}

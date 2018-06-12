contract BitSTDView{
    function migration(address add) public{}
    function transfer(address _to, uint256 _value) public {}
}
contract airDrop{
    /**
     * 
     *This is a fixed airdrop
     *
     * @param contractaddress this is Address of airdrop token contract
     * @param dsts this is Batch acceptance address
     * @param value this is Issuing number
     */
    function airDrop(address contractaddress,address[] dsts,uint256 value) public returns(bool){

        uint count= dsts.length;
        require(value>0);
        BitSTDView View= MTKBitSTDView;
        for(uint i = 0; i < count; i++){
           View.transfer(dsts[i],value);
        }
        
        return true;
    }
    /**
     * 
     * This is a multi-value airdrop
     *
     * @param contractaddress this is Address of airdrop token contract
     * @param dsts this is Batch acceptance address
     * @param values This is the distribution number array
     */
    function airDropValues(address contractaddress,address[] dsts,uint256[] values) public returns(bool){

        uint count= dsts.length;
        BitSTDView View= MTKBitSTDView;
        for(uint i = 0; i < count; i++){
           View.transfer(dsts[i],value[i]);
        }
        
        return true;
    }
    /**
     * 
     * This is a multi-value airdrop
     *
     * @param contractaddress this is Address of airdrop token contract
     * @param dsts This is the address where the data needs to be migrated
     */
    function dataMigration(address contractaddress,address[] dsts)public  returns(bool){
        uint count= dsts.length;
        BitSTDView View= MTKBitSTDView;
        for(uint i = 0; i < count; i++){
           View.migration(dsts[i]);
        }
        return true;
    }
   
}
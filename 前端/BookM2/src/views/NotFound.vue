<template>
  <div class="all">
  <div class="error-no"> 
    <span>4</span>
    <div class="moon"></div>
    <span>4</span>
  </div>
</div>
</template>

<script>
export default {

}
</script>

<style scoped>
.all {
  position: fixed;
  left: 50%;
  top: 50%;
  -webkit-transform: translate(-50%, -65%);
          transform: translate(-50%, -65%);
}
.error-no {
  font-size: 200px;
  font-weight: bold;
  display: -webkit-box;
  display: flex;
  -webkit-box-align: center;
          align-items: center;
  -webkit-box-pack: center;
          justify-content: center;
  color: #a4a8a8;
  text-shadow: -2px 4px 2px rgba(0, 0, 0, 0.5);
}

.moon {
  width: 150px;
  height: 150px;
  border-radius: 50%;
  background: #fff;
  position: relative;
  display: inline-block;
  z-index: 10;
  overflow: hidden;
  box-shadow: -20px 0 20px #e9e9e9 inset;
  border: 1px solid #a4a8a8;
}
.moon:after {
  content: '';
  position: absolute;
  left: 75px;
  top: 70%;
  width: 20px;
  height: 20px;
  border-radius: 50%;
  color: #e9e9e9;
  background: currentcolor;
  box-shadow: -30px -50px 0 20px, 50px -30px 0 -8px , 50px -90px 0 2px ,  100px -90px 0 0px,  100px -40px 0 25px, 150px -15px 0 0px,  240px 15px 0 4px, 230px -80px 0 2px,   200px -20px 0 16px, 350px  0 0, 320px -50px 0 20px, 400px -30px 0 -8px, 400px -90px 0 2px;
}
</style>
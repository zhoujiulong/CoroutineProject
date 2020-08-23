组件化开发例子
-------------
### 项目介绍
    通过gradle配置结合阿里ARouter实现的组件化开发
### 组件化实现
    1、在跟目录下创建 config_assembly.properties配置文件，里面写入对应模块的bool参数，如：
       AssemblyARun = false
       AssemblyBRun = false
    2、在组件model中读取配置文件中的值，并配置model的运行方式，如下
      Properties configAssemblyProp = new Properties()
      configAssemblyProp.load(new FileInputStream(file("$rootDir/config_assembly.properties")))

      Boolean isRun = Boolean.parseBoolean(configAssemblyProp['AssemblyARun'])
      if (isRun) {
          apply plugin: 'com.android.application'
      } else {
          apply plugin: 'com.android.library'
      }
    3、在组件model中创建另一个AndroidManifest.xml文件，当组件以单独项目运行的时候组件使用该文件
      sourceSets {
              main {
                  if (isRun) {
                      manifest.srcFile 'src/main/manifest/AndroidManifest.xml'
                  } else {
                      manifest.srcFile 'src/main/AndroidManifest.xml'
                  }
              }
          }
### 项目结构
    reslib：存放项目图片、通用style、适配文件等其它资源文件
    baselib：基础功能，如基类、工具类、网络请求封装、图片加载封装等
    widgetlib：存放自定义控件
    commonlib：存放项目通用组件，提供给各个组件和主项目使用，如登陆实体类等
    assembly_a：项目组件a
    assembly_b：项目组件b
    app：主项目
### 依赖关系
    reslib -> baselib -> widgetlib -> commonlib -> assembly_a/assembly_b -> app 
### 有几个需要注意的地方
    1、避免重复依赖，此案例中将所有的依赖库放到跟目录下的build.gradle中，在baselib中使用api方式
    添加全部依赖，在组件中根据运行方式动态设置使用compileOnly依赖或者使用api依赖
    2、避免资源文件重命名，如果有重命名的打包的时候会取主项目中的舍弃其它model中的，可以在每个model
    中的build.gradle中添加通配符进行资源文件名的限定，如： resourcePrefix "a_"
    3、组件间跳转，此案例采用的是阿里的ARouter进行跳转的
    
    
    
    
    

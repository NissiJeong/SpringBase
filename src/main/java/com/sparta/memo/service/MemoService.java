package com.sparta.memo.service;

import com.sparta.memo.dto.MemoRequestDto;
import com.sparta.memo.dto.MemoResponseDto;
import com.sparta.memo.entity.Memo;
import com.sparta.memo.repository.MemoRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class  MemoService {

    private final MemoRepository memoRepository;

    /*
    //ApplicationContext beanfactory등의 인터페이스를 상속 받아서 생성된 인터페이스
    //IoC container 인터페이스라고 생각하면 된다.
    public MemoServcie(ApplicationContext context) {
        //1. 'Bean' 이름으로 가져오기
        //MemoRepository memoRepository1 = (MemoRepository) context.getBean("memoRepository");

        //2. 'Bean' 클래스 형식으로 가져오기
        MemoRepository memoRepository1 = context.getBean(MemoRepository.class);
        this.memoRepository = memoRepository1;
    }*/

    public MemoService(MemoRepository memoRepository) {
        this.memoRepository = memoRepository;
    }

    public MemoResponseDto createMemo(MemoRequestDto requestDto) {
        // RequestDto -> Entity
        Memo memo = new Memo(requestDto);

        // DB 저장
        Memo saveMemo = memoRepository.save(memo);

        // Entity -> ResponseDto
        MemoResponseDto memoResponseDto = new MemoResponseDto(saveMemo);

        return memoResponseDto;
    }

    public List<MemoResponseDto> getMemos() {
        // DB 조회
        return memoRepository.findAll().stream().map(MemoResponseDto::new).toList();
    }

    @Transactional
    public Long updateMemo(Long id, MemoRequestDto requestDto) {
        // 해당 메모가 DB에 존재하는지 확인
        Memo memo = findMemo(id);

        memo.update(requestDto);
        return id;
    }

    public Long deleteMemo(Long id) {
        // 해당 메모가 DB에 존재하는지 확인
        Memo memo = findMemo(id);

        // Memo 삭제
        memoRepository.delete(memo);

        return id;

    }

    private Memo findMemo(Long id){
        return memoRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 메모는 존재하지 않습니다.")
        );
    }
}
